package com.sscott.cemeterytrackerv1.ui.add.cem

import android.Manifest
import android.content.*
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.os.IBinder
import android.provider.Settings
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import com.google.android.material.snackbar.Snackbar
import com.sscott.cemeterytrackerv1.BuildConfig
import com.sscott.cemeterytrackerv1.R
import com.sscott.cemeterytrackerv1.data.models.domain.CemeteryDomain
import com.sscott.cemeterytrackerv1.databinding.FragmentAddCemeteryBinding
import com.sscott.cemeterytrackerv1.location.BoundLocationService
import com.sscott.cemeterytrackerv1.other.Constants
import com.sscott.cemeterytrackerv1.other.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_add_cemetery.*
import timber.log.Timber
import java.lang.Exception
import java.time.OffsetDateTime
import java.util.*
import javax.inject.Inject


private const val REQUEST_LOCATION_PERMISSION_CODE = 34

@AndroidEntryPoint
class AddCemeteryFragment : Fragment() {

    private lateinit var binding: FragmentAddCemeteryBinding
    private var boundLocationService: BoundLocationService? = null
    private lateinit var localBroadcastReceiver: ForegroundBroadcastReceiver
    private var isLocationServiceBound = false
    private lateinit var geoCoder: Geocoder
    private val viewModel: AddCemViewModel by viewModels()

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    // Monitors connection to the while-in-use service.
    private val foregroundOnlyServiceConnection = object : ServiceConnection {

        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            Timber.i("onServiceConnected called For foregroundOnlyServiceConnection")
            val binder = service as BoundLocationService.LocalBinder //binder returned from service is how service is instantiated to get access to its methods
            boundLocationService = binder.service
            isLocationServiceBound = true
        }

        override fun onServiceDisconnected(name: ComponentName) {
            Timber.i("onServiceDisconnected called For foregroundOnlyServiceConnection")
            boundLocationService = null
            isLocationServiceBound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val animation = TransitionInflater.from(requireContext()).inflateTransition(
                android.R.transition.move
        )
        sharedElementEnterTransition = animation
        sharedElementReturnTransition = animation
        localBroadcastReceiver = ForegroundBroadcastReceiver()
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_cemetery, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.insertResponse.observe(viewLifecycleOwner) {
            it?.let {
                when (it.status) {
                    Status.SUCCESS -> {
                        binding.pbLocation.visibility = View.GONE

                        val option = NavOptions.Builder()
                                .setPopUpTo(R.id.addCemeteryFragment, true)
                                .build()
                        findNavController().navigate(AddCemeteryFragmentDirections.actionAddCemeteryFragmentToCemeteryDetailFragment(it.data!!.id), option)

                        //go to recently added cemetery fragment
                    }
                    Status.ERROR -> {
                        binding.pbLocation.visibility = View.GONE
                        Toast.makeText(requireContext(), "No network connection cemetery will be sent to server later", Toast.LENGTH_LONG).show()

                        //pops AddCemFragment off backstack leaving HomeFragment -> RecentlyAddCems on backstack
                        val option = NavOptions.Builder()
                                .setPopUpTo(R.id.addCemeteryFragment, true)
                                .build()
                        findNavController().navigate(AddCemeteryFragmentDirections.actionAddCemeteryFragmentToCemeteryDetailFragment(it.data!!.id), option)
                    }
                    Status.LOADING -> {
                        binding.pbLocation.visibility = View.VISIBLE

                    }
                }
            }
        }
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.fabAddCem.setOnClickListener {
            if (binding.etCemName.text.isNullOrBlank()) {
                Toast.makeText(requireContext(), "Please enter a name for the cemetery.", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.sendCemsToServer(
                        CemeteryDomain(
                                cemeteryId = UUID.randomUUID().mostSignificantBits,
                                name = binding.etCemName.text.toString(),
                                location = binding.etCemAddress.text.toString(),
                                state = binding.tvStateList.text.toString(),
                                county = binding.etCemCounty.text.toString(),
                                townShip = binding.etCemTownShip.text.toString(),
                                cemRange = binding.etCemRange.text.toString(),
                                spot = binding.etCemSpot.text.toString(),
                                firstYear = binding.etCemFirstYear.text.toString(),
                                cemSection = binding.etCemSection.text.toString(),
                                epochTimeAdded = OffsetDateTime.now().toEpochSecond(),
                                addedBy = sharedPreferences.getString(Constants.KEY_LOGGED_IN_USERNAME, Constants.NO_USERNAME)
                                        ?: "",
                                graveCount = 0,
                                graves = emptyList(),
                                isSynced = false
                        )
                )
            }

        }

        val stateListAdapter = ArrayAdapter(requireContext(), R.layout.state_item, resources.getStringArray(R.array.string_array_states))
        binding.tvStateList.setAdapter(stateListAdapter)

        tilCemAddress.setStartIconOnClickListener {
            Timber.i("cemAddress button clicked")
            if (foregroundPermissionApproved()) {
                Timber.i("foreground permission is approved calling subscribeToLocationUpdates")
                boundLocationService?.subscribeToLocationUpdates()
                binding.pbLocation.visibility = View.VISIBLE
            } else {
                Timber.i("foregroundPermission is not approved calling requestForegroundPermission()")
                requestForegroundPermission()
            }
        }
    }


    override fun onStart() {
        super.onStart()

        val serviceIntent = Intent(requireActivity(), BoundLocationService::class.java)
        //BIND_AUTO_CREATE - automatically creates service as long as the binding exists
        activity?.bindService(serviceIntent, foregroundOnlyServiceConnection, Context.BIND_AUTO_CREATE) //keeps connection open until explicitly removed
    }

    override fun onResume() {
        super.onResume()
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(
                localBroadcastReceiver,
                IntentFilter(BoundLocationService.ACTION_BOUND_LOCATION_BROADCAST)
        )
    }

    override fun onPause() {
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(
                localBroadcastReceiver
        )
        super.onPause()

    }

    override fun onStop() {
        boundLocationService?.unsubscribeToLocationUpdates()
        if (isLocationServiceBound) {
            activity?.unbindService(foregroundOnlyServiceConnection)
            isLocationServiceBound = false
        }
        super.onStop()
    }


    private inner class ForegroundBroadcastReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) { //onReceive() creates a new thread then is moved to background
            val location = intent?.getParcelableExtra<Location>(
                    BoundLocationService.EXTRA_LOCATION
            )
            Timber.i(location.toString())

            Timber.i("in onReceive of broadcase receiver")
            binding.pbLocation.visibility = View.GONE


            if (location != null) {
                geoCoder = Geocoder(requireActivity(), Locale.getDefault())

                var addressList: MutableList<Address?>? = null

                try {
                    addressList = geoCoder.getFromLocation(location.latitude, location.longitude, 1)
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                //Handle case when there is no network access (address list will be null)
                if (addressList?.get(0) != null) {
                    binding.etCemAddress.setText(addressList.get(0)?.getAddressLine(0).toString())
                    binding.tvStateList.setText(addressList.get(0)?.adminArea.toString())
                } else {
                    Toast.makeText(requireContext(), "Could not access location.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Snackbar.make(
                        binding.root,
                        "Could not find location.",
                        Snackbar.LENGTH_LONG,
                ).show()
            }
        }

    }

    private fun foregroundPermissionApproved(): Boolean {
        return PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
        )
    }

    private fun requestForegroundPermission() {
        val provideRationale = foregroundPermissionApproved()

        if (provideRationale) {
            Snackbar.make(
                    binding.root,
                    "Location permission is needed for cemetery's address",
                    Snackbar.LENGTH_LONG
            )
                    .setAction("OK") {
                        ActivityCompat.requestPermissions(
                                requireActivity(),
                                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                                REQUEST_LOCATION_PERMISSION_CODE
                        )
                    }.show()

        } else {
            ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_LOCATION_PERMISSION_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String>,
            grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_LOCATION_PERMISSION_CODE -> when {
                grantResults.isEmpty() -> {

                }
                grantResults[0] == PackageManager.PERMISSION_GRANTED -> {
                    boundLocationService?.subscribeToLocationUpdates()
                }
            }
            else -> {

                //permission denied

                Snackbar.make(
                        binding.root,
                        "Permission was denied, manually enter cemetery's location",
                        Snackbar.LENGTH_LONG
                )
                        .setAction("Settings") {
                            val intent = Intent()
                            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                            val uri = Uri.fromParts(
                                    "package",
                                    BuildConfig.APPLICATION_ID,
                                    AddCemeteryFragment().tag
                            )
                            intent.data = uri
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                        }.show()
            }
        }

    }


}