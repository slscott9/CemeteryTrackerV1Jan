package com.sscott.cemeterytrackerv1.ui.register

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sscott.cemeterytrackerv1.R
import com.sscott.cemeterytrackerv1.other.Constants.KEY_LOGGED_IN_EMAIL
import com.sscott.cemeterytrackerv1.other.Constants.KEY_LOGGED_IN_USERNAME
import com.sscott.cemeterytrackerv1.other.Constants.KEY_PASSWORD
import com.sscott.cemeterytrackerv1.other.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_register.*
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    @Inject
    lateinit var sharedPreferences : SharedPreferences
    private val viewModel : RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        viewModel.registerStatus.observe(viewLifecycleOwner){
            it?.let {
                when(it.status){
                    Status.SUCCESS -> {
                        registerProgressBar.visibility = View.GONE
                        sharedPreferences.edit().putString(KEY_LOGGED_IN_EMAIL, etEmail.text.toString()).apply()
                        sharedPreferences.edit().putString(KEY_LOGGED_IN_USERNAME, etUserName.text.toString()).apply()
                        sharedPreferences.edit().putString(KEY_PASSWORD, etPassword.text.toString()).apply()

                        findNavController().navigate(RegisterFragmentDirections.actionGlobalHomeFragment())
                    }

                    Status.ERROR -> {
                        registerProgressBar.visibility = View.GONE
                        Toast.makeText(requireActivity(), it.message, Toast.LENGTH_SHORT).show()
                    }

                    Status.LOADING -> {
                        registerProgressBar.visibility = View.VISIBLE
                    }
                }
            }
        }
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnRegister.setOnClickListener {
            viewModel.register(
                userName = etUserName.text.toString(),
                userEmail = etEmail.text.toString(),
                password = etPassword.text.toString()
            )
        }
    }


}