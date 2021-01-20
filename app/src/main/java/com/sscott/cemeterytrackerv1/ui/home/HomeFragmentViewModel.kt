package com.sscott.cemeterytrackerv1.ui.home

import android.content.SharedPreferences
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sscott.cemeterytrackerv1.data.models.domain.CemeteryDomain
import com.sscott.cemeterytrackerv1.data.repository.Repository
import com.sscott.cemeterytrackerv1.other.Constants
import com.sscott.cemeterytrackerv1.other.Resource
import kotlinx.coroutines.launch
import timber.log.Timber

class HomeFragmentViewModel @ViewModelInject constructor(
    private val repository: Repository,
    private val sharedPreferences: SharedPreferences
) : ViewModel(){

    private val _allCems =  MutableLiveData<Resource<List<CemeteryDomain>>>()
    val allCems : LiveData<Resource<List<CemeteryDomain>>> = _allCems

    private val _myCems =  MutableLiveData<Resource<List<CemeteryDomain>>>()
    val myCems : LiveData<Resource<List<CemeteryDomain>>> = _myCems

    private val userName = sharedPreferences.getString(Constants.KEY_LOGGED_IN_USERNAME, Constants.NO_USERNAME) ?: ""

    init {

        viewModelScope.launch {
            _allCems.value = repository.allCemeteries()
            _myCems.value = repository.myCemeteries(userName)
        }
    }

    fun refreshCemeteries() {
        viewModelScope.launch {

            Timber.i("UserName is $userName")
            _allCems.value = repository.allCemeteries()
            _myCems.value = repository.myCemeteries(userName)

        }
    }


}