package com.sscott.cemeterytrackerv1.ui.recentlyaddedcems

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.sscott.cemeterytrackerv1.R
import com.sscott.cemeterytrackerv1.databinding.FragmentRecentlyAddedCemBinding


class RecentlyAddedCemFragment : Fragment() {

    private lateinit var binding : FragmentRecentlyAddedCemBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recently_added_cem, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root

    }




}