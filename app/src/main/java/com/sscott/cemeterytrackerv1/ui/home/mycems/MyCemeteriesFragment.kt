package com.sscott.cemeterytrackerv1.ui.home.mycems

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.sscott.cemeterytrackerv1.R
import com.sscott.cemeterytrackerv1.databinding.FragmentMyCemeteriesBinding
import com.sscott.cemeterytrackerv1.ui.adapters.MyCemsListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyCemeteriesFragment : Fragment() {

    private lateinit var binding : FragmentMyCemeteriesBinding
    private lateinit var myCemsListAdapter: MyCemsListAdapter


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

        binding.swipeRefreshMyCems.setOnRefreshListener {
            Toast.makeText(requireActivity(), "Swipe listener invoked", Toast.LENGTH_SHORT).show()
        }




    }


}