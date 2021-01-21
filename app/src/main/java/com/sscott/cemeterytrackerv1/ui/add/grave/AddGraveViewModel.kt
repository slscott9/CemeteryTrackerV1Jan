package com.sscott.cemeterytrackerv1.ui.add.grave

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sscott.cemeterytrackerv1.data.models.domain.GraveDomain
import com.sscott.cemeterytrackerv1.data.repository.Repository
import com.sscott.cemeterytrackerv1.other.Resource
import kotlinx.coroutines.launch

class AddGraveViewModel @ViewModelInject constructor(
        private val repository: Repository
): ViewModel() {

    private val _addGraveResponse = MutableLiveData<Resource<GraveDomain>>()
    val addGraveResponse : LiveData<Resource<GraveDomain>> = _addGraveResponse

    fun sendGraveToNetwork(graveDomain: GraveDomain) {

        _addGraveResponse.postValue(Resource.loading(null))

        viewModelScope.launch {

            repository.insertGrave(graveDomain)
            _addGraveResponse.postValue(repository.sendGraveToNetwork(graveDomain))
        }
    }

}