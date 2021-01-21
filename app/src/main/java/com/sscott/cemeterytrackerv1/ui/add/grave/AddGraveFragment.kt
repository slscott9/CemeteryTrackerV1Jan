package com.sscott.cemeterytrackerv1.ui.add.grave

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sscott.cemeterytrackerv1.R
import com.sscott.cemeterytrackerv1.databinding.FragmentAddGraveBinding
import com.sscott.cemeterytrackerv1.other.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddGraveFragment : Fragment() {

    private lateinit var binding : FragmentAddGraveBinding
    private val viewModel : AddGraveViewModel by viewModels()

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
//                        findNavController().navigate(AddGraveFragmentDirections.actionAddGraveFragmentToGraveDetailFragment())
                    }
                    Status.LOADING -> {

                    }
                    Status.ERROR -> {

                    }
                }
            }
        }

        binding.fabAddGrave.setOnClickListener {

        }

    }



}