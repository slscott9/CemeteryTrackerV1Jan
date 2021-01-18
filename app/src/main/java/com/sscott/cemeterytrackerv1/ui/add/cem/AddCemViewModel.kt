package com.sscott.cemeterytrackerv1.ui.add.cem

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sscott.cemeterytrackerv1.data.models.network.CemeteryDto
import com.sscott.cemeterytrackerv1.data.repository.Repository
import com.sscott.cemeterytrackerv1.other.Resource
import kotlinx.coroutines.launch

class AddCemViewModel @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _cemeteryResponse = MutableLiveData<Resource<CemeteryDto>>()
    val cemeteryResponse : LiveData<Resource<CemeteryDto>> = _cemeteryResponse

    fun sendCemsToServer(cemeteryDto: CemeteryDto) {

        _cemeteryResponse.postValue(Resource.loading(null))

        viewModelScope.launch {
            _cemeteryResponse.postValue(
                repository.sendCemToNetwork(cemeteryDto)
            )
        }

    }

    fun resetCemResponse() {
        _cemeteryResponse.value = null
    }
}