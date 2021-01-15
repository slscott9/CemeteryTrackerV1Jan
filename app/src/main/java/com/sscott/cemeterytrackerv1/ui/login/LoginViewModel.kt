package com.sscott.cemeterytrackerv1.ui.login

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sscott.cemeterytrackerv1.data.models.network.UserDto
import com.sscott.cemeterytrackerv1.data.repository.Repository
import com.sscott.cemeterytrackerv1.other.Resource
import kotlinx.coroutines.launch

class LoginViewModel @ViewModelInject constructor(
        private val repository: Repository
) : ViewModel() {

    private val _loginStatus = MutableLiveData<Resource<UserDto>>()
    val loginStatus : LiveData<Resource<UserDto>> = _loginStatus

    fun login(userName: String, password : String) { //Dispatcher.main user postValue() since we will be on Main thread

        _loginStatus.postValue(Resource.loading(null))

        if(userName.isEmpty() || password.isEmpty()){
            _loginStatus.postValue(Resource.error("Please fill out all fields", null))
            return
        }

        viewModelScope.launch {
            val resource = repository.login( //Dispatcher.IO
                    UserDto(
                            email = "",
                            userName = userName,
                            password = password,
                            gravesAdded = null,
                            cemeteriesAdded = null
                    )
            )

            _loginStatus.postValue(resource)

        }
    }
}