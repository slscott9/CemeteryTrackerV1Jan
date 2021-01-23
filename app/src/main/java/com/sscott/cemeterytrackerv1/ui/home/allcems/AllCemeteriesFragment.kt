package com.sscott.cemeterytrackerv1.ui.home.allcems

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sscott.cemeterytrackerv1.R
import com.sscott.cemeterytrackerv1.databinding.FragmentAllCemeteriesBinding
import com.sscott.cemeterytrackerv1.other.Status
import com.sscott.cemeterytrackerv1.ui.adapters.AllCemsListAdapter
import com.sscott.cemeterytrackerv1.ui.home.HomeFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllCemeteriesFragment : Fragment() {

    private lateinit var binding : FragmentAllCemeteriesBinding
    private val viewModel : AllCemsViewModel by activityViewModels()
    private lateinit var allCemsListAdapter: AllCemsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_all_cemeteries, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        setupSearch()


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.strAllCems.setOnRefreshListener {

            viewModel.refreshCemsList()
            binding.strAllCems.isRefreshing = false

        }

        allCemsListAdapter = AllCemsListAdapter {

        }

        binding.rvAllCems.adapter = allCemsListAdapter

        viewModel.allCemeteries.observe(viewLifecycleOwner){
            it?.let {
                when(it.status){
                    Status.SUCCESS -> {
                        binding.progressBarAllCems.visibility = View.GONE
                        allCemsListAdapter.submitList(it.data)
                    }
                    Status.ERROR -> {
                        binding.progressBarAllCems.visibility = View.GONE
                        Toast.makeText(requireActivity(), it.message, Toast.LENGTH_SHORT).show()
                    }
                    Status.LOADING -> {
                        binding.progressBarAllCems.visibility = View.VISIBLE
                    }
                }
            }
        }
        viewModel.cemeterySearchResult.observe(viewLifecycleOwner){
            it?.let {
                allCemsListAdapter.submitList(it)
            }
        }

        binding.rvAllCems.adapter = allCemsListAdapter

    }

    private fun setupSearch() {
        binding.svAllCems.setOnQueryTextListener(
                object : android.widget.SearchView.OnQueryTextListener,
                        SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        newText?.let { viewModel.setSearchQuery(it) } //as user types searchQuery it is offered to subscribers which triggers repo.getSearchCemList
                        return true
                    }
                }
        )

        binding.svAllCems.setOnCloseListener {
            allCemsListAdapter.submitList(viewModel.getCemsList().value?.data ?: emptyList())
            true
        }
    }


}