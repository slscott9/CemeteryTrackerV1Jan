package com.sscott.cemeterytrackerv1.ui.home

import android.content.SharedPreferences
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.sscott.cemeterytrackerv1.data.models.domain.CemeteryDomain
import com.sscott.cemeterytrackerv1.data.repository.Repository
import com.sscott.cemeterytrackerv1.other.Constants
import com.sscott.cemeterytrackerv1.other.Resource
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapLatest
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

    private val searchChannel = ConflatedBroadcastChannel<String>()

    val cemeterySearchResult = searchChannel.asFlow()
            .flatMapLatest { searchQuery -> repository.getCemsFromSearch(searchQuery) }.asLiveData()



    fun setSearchQuery(searchQuery : String){
        searchChannel.offer(searchQuery)
    }


    fun refreshCemeteries() {
        viewModelScope.launch {

            Timber.i("UserName is $userName")
            _allCems.value = repository.allCemeteries()
            _myCems.value = repository.myCemeteries(userName)

        }
    }


}