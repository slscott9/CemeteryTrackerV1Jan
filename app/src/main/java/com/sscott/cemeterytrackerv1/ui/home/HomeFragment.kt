package com.sscott.cemeterytrackerv1.ui.home

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.sscott.cemeterytrackerv1.R
import com.sscott.cemeterytrackerv1.databinding.FragmentHomeBinding
import com.sscott.cemeterytrackerv1.other.Constants.KEY_LOGGED_IN_EMAIL
import com.sscott.cemeterytrackerv1.other.Constants.KEY_PASSWORD
import com.sscott.cemeterytrackerv1.other.Constants.NO_EMAIL
import com.sscott.cemeterytrackerv1.other.Constants.NO_PASSWORD
import com.sscott.cemeterytrackerv1.ui.adapters.ALLCEMS_INDEX
import com.sscott.cemeterytrackerv1.ui.adapters.CemViewPagerAdapter
import com.sscott.cemeterytrackerv1.ui.adapters.MYCEMS_INDEX
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        val tabLayout = binding.cemTabLayout
        val viewPager = binding.cemViewPager

        viewPager.adapter = CemViewPagerAdapter(this)

        TabLayoutMediator(tabLayout, viewPager){tab, pos ->
        tab.text = getTabTitle(pos)
        }.attach()

        return binding.root
    }

    fun getTabTitle(position : Int): String {
        return when(position){
            MYCEMS_INDEX -> "My Cemeteries"
            ALLCEMS_INDEX -> "All Cemeteries"
            else -> ""
        }
    }



}