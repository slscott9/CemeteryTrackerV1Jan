package com.sscott.cemeterytrackerv1.ui.add.cem

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sscott.cemeterytrackerv1.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddCemeteryFragment : Fragment() {




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_cemetery, container, false)
    }


}