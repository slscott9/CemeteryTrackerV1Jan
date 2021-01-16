package com.sscott.cemeterytrackerv1.ui.home.allcems

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sscott.cemeterytrackerv1.data.models.domain.CemeteryDomain
import com.sscott.cemeterytrackerv1.data.repository.Repository
import com.sscott.cemeterytrackerv1.other.Resource
import kotlinx.coroutines.launch

class AllCemsViewModel @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel() {



    private val _allCems =  MutableLiveData<Resource<List<CemeteryDomain>>>()
    val allCems : LiveData<Resource<List<CemeteryDomain>>> = _allCems

    init {

        viewModelScope.launch {
            _allCems.value = repository.allCemeteries()

        }
    }



    fun refreshCemsList() {
        viewModelScope.launch {
            _allCems.value = repository.allCemeteries()
        }
    }
}