package com.sscott.cemeterytrackerv1.ui.detail.cemetery

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sscott.cemeterytrackerv1.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CemeteryDetailFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cemetery_detail, container, false)
    }

}