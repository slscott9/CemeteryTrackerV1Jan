package com.sscott.cemeterytrackerv1.ui.detail.cemetery

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
import com.sscott.cemeterytrackerv1.databinding.FragmentCemeteryDetailBinding
import com.sscott.cemeterytrackerv1.other.Status
import com.sscott.cemeterytrackerv1.ui.adapters.GraveListAdapter
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class CemeteryDetailFragment : Fragment() {

    private lateinit var binding : FragmentCemeteryDetailBinding
    private val navArgs : CemeteryDetailFragmentArgs by navArgs()
    private val viewModel : CemeteryDetailViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cemetery_detail, container, false)
        binding.lifecycleOwner = viewLifecycleOwner


        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val graveListAdapter = GraveListAdapter(GraveListAdapter.GraveListListener {

        })

        binding.rvGraves.adapter = graveListAdapter

        binding.fabAddGrave.setOnClickListener {
            findNavController().navigate(CemeteryDetailFragmentDirections.actionCemeteryDetailFragmentToAddGraveFragment(navArgs.cemeteryId))
        }



        viewModel.cemResponse.observe(viewLifecycleOwner){
            it?.let {
                when(it.status){
                    Status.SUCCESS -> {
                        Timber.i("Success Status")
                        binding.pbGetGrave.visibility = View.GONE
                        binding.cemetery = it.data
                        graveListAdapter.submitList(it.data?.graves)

                    }
                    Status.ERROR -> {
                        Timber.i("Error Status")
                        binding.pbGetGrave.visibility = View.GONE
//                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                    Status.LOADING -> {
                        Timber.i("Loading Status")
                        binding.pbGetGrave.visibility = View.VISIBLE
                    }
                }
            }
        }



    }

}