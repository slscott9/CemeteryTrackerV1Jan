package com.sscott.cemeterytrackerv1.ui.add.cem

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sscott.cemeterytrackerv1.data.models.domain.CemeteryDomain
import com.sscott.cemeterytrackerv1.data.models.network.cemdto.CemeteryDto
import com.sscott.cemeterytrackerv1.data.repository.Repository
import com.sscott.cemeterytrackerv1.other.InsertResponse
import com.sscott.cemeterytrackerv1.other.Resource
import com.sscott.cemeterytrackerv1.other.Status
import kotlinx.coroutines.launch
import timber.log.Timber

class AddCemViewModel @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _insertResponse = MutableLiveData<Resource<InsertResponse>>()
    val insertResponse : LiveData<Resource<InsertResponse>> = _insertResponse

    fun sendCemsToServer(cemetery: CemeteryDomain) {

        _insertResponse.postValue(Resource.loading(null))

        viewModelScope.launch {

            val id = repository.insertCemetery(cemetery)

            Timber.i(id.toString())


            val response = repository.sendCem(cemetery)

            when(response.status){
                Status.SUCCESS -> {
                    repository.updateCemetery(cemetery.copy(isSynced = true))
                    _insertResponse.postValue(Resource.success(InsertResponse(id = id, message = "")))
                }
                Status.ERROR -> {
                    _insertResponse.postValue(Resource.error( data = InsertResponse(id = id, message = ""),msg = response.message.toString()))

                }
            }
        }
    }


}