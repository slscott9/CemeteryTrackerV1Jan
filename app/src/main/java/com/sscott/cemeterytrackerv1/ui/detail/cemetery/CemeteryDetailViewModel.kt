package com.sscott.cemeterytrackerv1.ui.detail.cemetery

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.sscott.cemeterytrackerv1.data.models.domain.CemeteryDomain
import com.sscott.cemeterytrackerv1.data.repository.Repository
import com.sscott.cemeterytrackerv1.other.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import timber.log.Timber

class CemeteryDetailViewModel @ViewModelInject constructor(
        private val repository: Repository,
        @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

//    val cemetery = liveData {
//        emit(repository.getCemetery(savedStateHandle.get<Long>("cemeteryId")!!))
//    }


    val cemResponse: LiveData<Resource<CemeteryDomain>> =
        liveData(viewModelScope.coroutineContext) {
            emit(repository.getNetworkCemetery(savedStateHandle.get<Long>("cemeteryId")!!))
        }






}