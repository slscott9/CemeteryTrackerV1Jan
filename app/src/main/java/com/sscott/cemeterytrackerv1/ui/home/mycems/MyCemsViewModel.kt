package com.sscott.cemeterytrackerv1.ui.home.mycems

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.sscott.cemeterytrackerv1.data.models.domain.CemeteryDomain
import com.sscott.cemeterytrackerv1.data.repository.Repository
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class MyCemsViewModel @ViewModelInject constructor(
        private val repository: Repository
) : ViewModel(){

    val myCemeteries =
            repository.allLocalCems().asLiveData()


    private val searchChannel = ConflatedBroadcastChannel<String>()
    val cemeterySearchResult = searchChannel.asFlow()
            .flatMapLatest { searchQuery -> repository.searchLocalCems(searchQuery) }.asLiveData()

    fun setSearchQuery(searchQuery : String){
        searchChannel.offer(searchQuery)
    }


    fun getCemsList() : LiveData<List<CemeteryDomain>>{
        return myCemeteries
    }
}