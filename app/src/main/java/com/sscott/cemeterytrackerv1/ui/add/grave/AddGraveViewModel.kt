package com.sscott.cemeterytrackerv1.ui.add.grave

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sscott.cemeterytrackerv1.data.models.domain.GraveDomain
import com.sscott.cemeterytrackerv1.data.repository.Repository
import com.sscott.cemeterytrackerv1.other.InsertResponse
import com.sscott.cemeterytrackerv1.other.Resource
import com.sscott.cemeterytrackerv1.other.Status
import kotlinx.coroutines.launch

/*
    insert grave in local db -> returns id

    send grave to network error? -> show toast let work manager sync

    success -> move on to cem detail
 */

class AddGraveViewModel @ViewModelInject constructor(
        private val repository: Repository
): ViewModel() {

    private val _addGraveResponse = MutableLiveData<Resource<InsertResponse>>()
    val addGraveResponse : LiveData<Resource<InsertResponse>> = _addGraveResponse

    fun sendGraveToNetwork(graveDomain: GraveDomain) {

        _addGraveResponse.postValue(Resource.loading(null))

        viewModelScope.launch {

            val id = repository.insertGrave(graveDomain)

            val response = repository.sendGrave(graveDomain)

            when(response.status){
                Status.SUCCESS -> {
                    repository.updateGrave(graveDomain.copy(isSynced = true))
                    _addGraveResponse.postValue(
                        Resource.success(InsertResponse(id = id, message = "")
                        ))
                }
                Status.ERROR -> {
                    _addGraveResponse.postValue(
                        Resource.error( data = InsertResponse(id = id, message = ""),
                            msg = response.message.toString()
                        ))

                }
            }
        }
    }

}