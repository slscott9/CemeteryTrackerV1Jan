package com.sscott.cemeterytrackerv1.ui.home.allcems

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sscott.cemeterytrackerv1.R
import com.sscott.cemeterytrackerv1.databinding.FragmentAllCemeteriesBinding
import com.sscott.cemeterytrackerv1.other.Status
import com.sscott.cemeterytrackerv1.ui.adapters.AllCemsListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllCemeteriesFragment : Fragment() {

    private lateinit var binding : FragmentAllCemeteriesBinding
    private val viewModel : AllCemsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_all_cemeteries, container, false)
        binding.lifecycleOwner = viewLifecycleOwner


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.swipeRefreshAllCems.setOnRefreshListener {
            viewModel.refreshCemsList()
        }

        val allCemsListAdapter = AllCemsListAdapter {

        }

        binding.rvAllCems.adapter = allCemsListAdapter

        viewModel.allCems.observe(viewLifecycleOwner){
            it?.let {
                when(it.status){
                    Status.SUCCESS -> {
                        binding.progressBarAllCems.visibility = View.GONE
                        binding.swipeRefreshAllCems.isRefreshing = false
                        allCemsListAdapter.submitList(it.data)
                    }
                    Status.ERROR -> {
                        binding.progressBarAllCems.visibility = View.GONE
                        binding.swipeRefreshAllCems.isRefreshing = false
                        Toast.makeText(requireActivity(), it.message, Toast.LENGTH_SHORT).show()
                    }
                    Status.LOADING -> {
                        binding.progressBarAllCems.visibility = View.VISIBLE
                    }
                }
            }
        }

        binding.rvAllCems.adapter = allCemsListAdapter
    }


}