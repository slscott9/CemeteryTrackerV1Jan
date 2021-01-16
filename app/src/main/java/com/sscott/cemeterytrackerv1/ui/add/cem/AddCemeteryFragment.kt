package com.sscott.cemeterytrackerv1.ui.add.cem

import android.Manifest
import android.content.*
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.SettingInjectorService
import android.net.Uri
import android.os.Bundle
import android.os.IBinder
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.transition.TransitionInflater
import com.google.android.material.snackbar.Snackbar
import com.sscott.cemeterytrackerv1.BuildConfig
import com.sscott.cemeterytrackerv1.R
import com.sscott.cemeterytrackerv1.databinding.FragmentAddCemeteryBinding
import com.sscott.cemeterytrackerv1.location.ForeGroundLocationService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_add_cemetery.*
import timber.log.Timber
import java.util.*


private const val REQUEST_FOREGROUND_ONLY_PERMISSIONS_REQUEST_CODE = 34

@AndroidEntryPoint
class AddCemeteryFragment : Fragment() {

    private lateinit var binding : FragmentAddCemeteryBinding
    private   var foregroundLocationService: ForeGroundLocationService? = null
    private lateinit var foregroundBroadcastReceiver : ForegroundBroadcastReceiver
    private var foregroundOnlyLocationServiceBound = false
    private lateinit var geoCoder : Geocoder


    // Monitors connection to the while-in-use service.
    private val foregroundOnlyServiceConnection = object : ServiceConnection {

        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            Timber.i("onServiceConnected called For foregroundOnlyServiceConnection")
            val binder = service as ForeGroundLocationService.LocalBinder
            foregroundLocationService = binder.service
            foregroundOnlyLocationServiceBound = true
        }

        override fun onServiceDisconnected(name: ComponentName) {
            Timber.i("onServiceDisconnected called For foregroundOnlyServiceConnection")
            foregroundLocationService = null
            foregroundOnlyLocationServiceBound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val animation = TransitionInflater.from(requireContext()).inflateTransition(
            android.R.transition.move
        )
        sharedElementEnterTransition = animation
        sharedElementReturnTransition = animation
        foregroundBroadcastReceiver = ForegroundBroadcastReceiver()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_cemetery, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val stateListAdapter = ArrayAdapter(requireContext(), R.layout.state_item, resources.getStringArray(R.array.string_array_states))
        binding.tvStateList.setAdapter(stateListAdapter)

        tilCemAddress.setStartIconOnClickListener {
            Timber.i("cemAddress button clicked")
            if(foregroundPermissionApproved()){
                Timber.i("foreground permission is approved calling subscribeToLocationUpdates")
                foregroundLocationService?.subscribeToLocationUpdates()
                binding.pbLocation.visibility = View.VISIBLE
            }else{
                Timber.i("foregroundPermission is not approved calling requestForegroundPermission()")
                requestForegroundPermission()
            }
        }
    }

    override fun onStart() {
        super.onStart()

        val serviceIntent = Intent(requireActivity(), ForeGroundLocationService::class.java)
        activity?.bindService(serviceIntent, foregroundOnlyServiceConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onResume() {
        super.onResume()
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(
            foregroundBroadcastReceiver,
            IntentFilter(ForeGroundLocationService.ACTION_FOREGROUND_ONLY_LOCATION_BROADCAST)
        )
    }

    override fun onPause() {
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(
            foregroundBroadcastReceiver
        )
        super.onPause()

    }

    override fun onStop() {
        if(foregroundOnlyLocationServiceBound){
            activity?.unbindService(foregroundOnlyServiceConnection)
        }
        foregroundLocationService?.unsubscribeToLocationUpdates()
        super.onStop()
    }

    private inner class ForegroundBroadcastReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val location = intent?.getParcelableExtra<Location>(
                ForeGroundLocationService.EXTRA_LOCATION
            )

            Timber.i("in onReceive of broadcase receiver")
            binding.pbLocation.visibility = View.GONE
            if(location != null){
                geoCoder = Geocoder(requireActivity(), Locale.getDefault())
                val addressList = geoCoder.getFromLocation(location.latitude, location.longitude, 1)
                binding.etCemAddress.setText(addressList[0].getAddressLine(0).toString())
                binding.tvStateList.setText(addressList[0].adminArea.toString())

            }else{
                Snackbar.make(
                    binding.root,
                    "Could not find location.",
                    Snackbar.LENGTH_LONG,
                ).show()
            }
        }

    }

    private fun foregroundPermissionApproved() : Boolean {
        return PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }

    private fun requestForegroundPermission() {
        val provideRationale = foregroundPermissionApproved()

        if(provideRationale){
            Snackbar.make(
                binding.root,
                "Location permission is needed for cemetery's address",
                Snackbar.LENGTH_LONG
            )
                .setAction("OK"){
                    ActivityCompat.requestPermissions(
                        requireActivity(),
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        REQUEST_FOREGROUND_ONLY_PERMISSIONS_REQUEST_CODE
                    )
                }.show()

        }else{
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_FOREGROUND_ONLY_PERMISSIONS_REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode : Int,
        permissions: Array<String>,
        grantResults : IntArray
    ) {
        when(requestCode){
            REQUEST_FOREGROUND_ONLY_PERMISSIONS_REQUEST_CODE -> when{
                grantResults.isEmpty() ->{

                }
                grantResults[0] == PackageManager.PERMISSION_GRANTED -> {
                    foregroundLocationService?.subscribeToLocationUpdates()
                }
            }
            else -> {

                //permission denied

                Snackbar.make(
                    binding.root,
                    "Permission was denied, manually enter cemetery's location",
                    Snackbar.LENGTH_LONG
                )
                    .setAction("Settings"){
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