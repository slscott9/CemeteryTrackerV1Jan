package com.sscott.cemeterytrackerv1.ui.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sscott.cemeterytrackerv1.ui.home.allcems.AllCemeteriesFragment
import com.sscott.cemeterytrackerv1.ui.home.mycems.MyCemeteriesFragment


const val MYCEMS_INDEX = 0
const val ALLCEMS_INDEX = 1


class CemViewPagerAdapter(fragment : Fragment) : FragmentStateAdapter(fragment) {

    private val tabCreator = mapOf(
        MYCEMS_INDEX to MyCemeteriesFragment(),
        ALLCEMS_INDEX to AllCemeteriesFragment()
    )

    override fun getItemCount(): Int {
        return tabCreator.size
    }

    override fun createFragment(position: Int): Fragment {
        return tabCreator[position]!!
    }


}