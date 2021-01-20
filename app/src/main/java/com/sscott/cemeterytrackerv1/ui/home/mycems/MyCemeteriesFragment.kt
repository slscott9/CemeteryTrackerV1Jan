package com.sscott.cemeterytrackerv1.ui.home.mycems

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.sscott.cemeterytrackerv1.R
import com.sscott.cemeterytrackerv1.databinding.FragmentMyCemeteriesBinding
import com.sscott.cemeterytrackerv1.other.Status
import com.sscott.cemeterytrackerv1.ui.adapters.MyCemsListAdapter
import com.sscott.cemeterytrackerv1.ui.home.HomeFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyCemeteriesFragment : Fragment() {

    private lateinit var binding : FragmentMyCemeteriesBinding
    private lateinit var myCemsListAdapter: MyCemsListAdapter
    private val viewModel : HomeFragmentViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_cemeteries, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myCemsListAdapter = MyCemsListAdapter(){

        }



        viewModel.myCems.observe(viewLifecycleOwner){
            it?.let {
                when(it.status){
                    Status.SUCCESS -> {
                        binding.pbMyCems.visibility = View.GONE
                        myCemsListAdapter.submitList(it.data)
                    }
                    Status.ERROR -> {
                        binding.pbMyCems.visibility = View.GONE
                        Toast.makeText(requireActivity(), it.message, Toast.LENGTH_SHORT).show()
                    }
                    Status.LOADING -> {
                        binding.pbMyCems.visibility = View.VISIBLE
                    }
                }
            }
        }

        binding.rvMyCems.adapter = myCemsListAdapter




    }


}