package com.sscott.cemeterytrackerv1.ui.add.cem

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.transition.TransitionInflater
import com.sscott.cemeterytrackerv1.R
import com.sscott.cemeterytrackerv1.databinding.FragmentAddCemeteryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddCemeteryFragment : Fragment() {

    private lateinit var binding : FragmentAddCemeteryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val animation = TransitionInflater.from(requireContext()).inflateTransition(
            android.R.transition.move
        )
        sharedElementEnterTransition = animation
        sharedElementReturnTransition = animation
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_cemetery, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }


}