package com.sscott.cemeterytrackerv1.ui.detail.cemetery

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.sscott.cemeterytrackerv1.R
import com.sscott.cemeterytrackerv1.databinding.FragmentCemeteryDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CemeteryDetailFragment : Fragment() {

    private lateinit var binding : FragmentCemeteryDetailBinding
    private val navArgs : CemeteryDetailFragmentArgs by navArgs()
    private val viewModel : CemeteryDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cemetery_detail, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel



    }

}