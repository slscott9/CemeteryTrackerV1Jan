package com.sscott.cemeterytrackerv1.ui.detail.cemetery

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.sscott.cemeterytrackerv1.data.models.domain.CemeteryDomain
import com.sscott.cemeterytrackerv1.data.repository.Repository

class CemeteryDetailViewModel @ViewModelInject constructor(
        private val repository: Repository,
        @Assisted savedStateHandle: SavedStateHandle
) : ViewModel(){

    val cemetery = repository.getCemetery(savedStateHandle.get<Long>("cemeteryId")!!)







}