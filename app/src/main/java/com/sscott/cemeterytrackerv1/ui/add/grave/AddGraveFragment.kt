package com.sscott.cemeterytrackerv1.ui.add.grave

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.sscott.cemeterytrackerv1.R
import com.sscott.cemeterytrackerv1.databinding.FragmentAddGraveBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddGraveFragment : Fragment() {

    private lateinit var binding : FragmentAddGraveBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_grave, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

}