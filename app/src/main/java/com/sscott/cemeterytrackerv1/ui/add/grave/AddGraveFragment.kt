package com.sscott.cemeterytrackerv1.ui.add.grave

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.sscott.cemeterytrackerv1.R
import com.sscott.cemeterytrackerv1.data.models.domain.GraveDomain
import com.sscott.cemeterytrackerv1.databinding.FragmentAddGraveBinding
import com.sscott.cemeterytrackerv1.other.Constants
import com.sscott.cemeterytrackerv1.other.Status
import dagger.hilt.android.AndroidEntryPoint
import java.time.OffsetDateTime
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class AddGraveFragment : Fragment() {

    private lateinit var binding : FragmentAddGraveBinding
    private val viewModel : AddGraveViewModel by viewModels()
    private val navArgs : AddGraveFragmentArgs by navArgs()

    @Inject
    lateinit var sharedPreferences : SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_grave, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.addGraveResponse.observe(viewLifecycleOwner){
            it?.let {
                when(it.status){
                    Status.SUCCESS -> {
                        binding.pbAddGrave.visibility = View.GONE
                        findNavController().navigate(AddGraveFragmentDirections.actionAddGraveFragmentToGraveDetailFragment(it.data!!.id))
                    }
                    Status.LOADING -> {
                        binding.pbAddGrave.visibility = View.VISIBLE
                    }
                    Status.ERROR -> {
                        binding.pbAddGrave.visibility = View.GONE
                        Toast.makeText(requireContext(), "No network connection grave will be sent to server later.", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(AddGraveFragmentDirections.actionAddGraveFragmentToGraveDetailFragment(it.data!!.id))
                    }
                }
            }
        }

        binding.fabAddGrave.setOnClickListener {
            viewModel.sendGraveToNetwork(
                GraveDomain(
                    firstName = binding.etFirstName.text.toString(),
                    lastName = binding.etLastName.text.toString(),
                    birthDate = binding.etBirthDate.text.toString(),
                    deathDate = binding.etDeathDate.text.toString(),
                    marriageYear = binding.etMarriageYear.text.toString(),
                    comment = binding.etComment.text.toString(),
                    graveNumber = binding.etGraveNum.text.toString(),
                    addedBy = sharedPreferences.getString(Constants.KEY_LOGGED_IN_USERNAME, Constants.NO_USERNAME) ?: "",
                    cemeteryId= navArgs.cemeteryId,
                    epochTimeAdded = OffsetDateTime.now().toEpochSecond(),
                    graveId = UUID.randomUUID().mostSignificantBits,
                        isSynced = false

                )
            )
        }

    }



}