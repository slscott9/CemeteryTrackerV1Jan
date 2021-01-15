package com.sscott.cemeterytrackerv1.ui.register

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sscott.cemeterytrackerv1.data.models.network.UserDto
import com.sscott.cemeterytrackerv1.data.repository.Repository
import com.sscott.cemeterytrackerv1.other.Resource
import kotlinx.coroutines.launch

class RegisterViewModel @ViewModelInject constructor(
        private val repository: Repository
) : ViewModel() {

    private val _registerStatus = MutableLiveData<Resource<UserDto>>()
    val registerStatus: LiveData<Resource<UserDto>> = _registerStatus

    fun register(userEmail: String, userName: String, password : String) { //postValue is called when main thread is available

//        Timber.i("username is $userName, password is $password, email is $userEmail")

        _registerStatus.postValue(Resource.loading(null))

        if(userEmail.isEmpty() || password.isEmpty() || userName.isEmpty()){
            _registerStatus.postValue(Resource.error("Please fill in all fields", null))
            return
        }

        viewModelScope.launch {
            val resource = repository.register( //Dispatcher.IO
                    UserDto(
                            userName = userName,
                            email = userEmail,
                            password = password,
                            gravesAdded = 0,
                            cemeteriesAdded = 0
                    )
            )

            _registerStatus.postValue(resource)
        }
    }
}