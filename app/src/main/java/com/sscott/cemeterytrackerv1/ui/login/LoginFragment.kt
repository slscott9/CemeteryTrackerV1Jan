package com.sscott.cemeterytrackerv1.ui.login

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
import com.sscott.cemeterytrackerv1.other.Constants
import com.sscott.cemeterytrackerv1.other.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_login.*
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {

    @Inject
    lateinit var sharedPreferences : SharedPreferences
    private val viewModel: LoginViewModel by viewModels()
    private var currentEmail : String? = null
    private var currentPassword : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(isLoggedIn()){
            findNavController().navigate(LoginFragmentDirections.actionGlobalHomeFragment())
        }
    }

    /*
            Created state
            This is the appropriate place to set up the initial state of your view, to start observing LiveData instances whose callbacks update the fragment's view,
            and to set up adapters on any RecyclerView or ViewPager2 instances in your fragment's view.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel.loginStatus.observe(viewLifecycleOwner){
            it?.let {
                when(it.status){
                    Status.SUCCESS -> {
                        loginProgressBar.visibility = View.GONE
                        findNavController().navigate(LoginFragmentDirections.actionGlobalHomeFragment())
                    }

                    Status.ERROR -> {
                        loginProgressBar.visibility = View.GONE
                        Toast.makeText(requireActivity(), it.message, Toast.LENGTH_SHORT).show()
                    }

                    Status.LOADING -> {
                        loginProgressBar.visibility = View.VISIBLE
                    }
                }
            }
        }
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnRegister.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }

        btnSignin.setOnClickListener {
            viewModel.login(userName = etUserName.text.toString(), password = etPassword.text.toString())
        }
    }

    private fun isLoggedIn() : Boolean{
        currentEmail = sharedPreferences.getString(
            Constants.KEY_LOGGED_IN_EMAIL,
            Constants.NO_EMAIL
        ) ?: Constants.NO_EMAIL
        currentPassword = sharedPreferences.getString(
            Constants.KEY_PASSWORD,
            Constants.NO_PASSWORD
        ) ?: Constants.NO_PASSWORD

        return currentEmail != Constants.NO_EMAIL && currentPassword != Constants.NO_PASSWORD
    }


}