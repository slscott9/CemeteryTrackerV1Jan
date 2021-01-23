package com.sscott.cemeterytrackerv1.ui.home.allcems

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.sscott.cemeterytrackerv1.data.models.domain.CemeteryDomain
import com.sscott.cemeterytrackerv1.data.repository.Repository
import com.sscott.cemeterytrackerv1.other.Resource
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

class AllCemsViewModel @ViewModelInject constructor(
        private val repository: Repository
) : ViewModel(){

    private val _allCemeteries = MutableLiveData<Resource<List<CemeteryDomain>>>()
    val allCemeteries : LiveData<Resource<List<CemeteryDomain>>> = _allCemeteries

    init {
        refreshCemsList()
    }

    private val searchChannel = ConflatedBroadcastChannel<String>()

    val cemeterySearchResult = searchChannel.asFlow()
            .debounce(300)
            .distinctUntilChanged()
            .flatMapLatest { searchQuery ->
                repository.searchNetworkCems(searchQuery)
                        .catch { e -> Resource.error("Check network connection", null) }
            }.asLiveData(viewModelScope.coroutineContext)


    fun setSearchQuery(searchQuery : String){
        searchChannel.offer(searchQuery)
    }

    fun refreshCemsList() {
        viewModelScope.launch {

            _allCemeteries.postValue(Resource.loading(null))

            repository.allNetworkCems()
                    .catch { e ->
                        Timber.i(e)
                        _allCemeteries.postValue(Resource.error("Check network connection", null)) }
                    .collect { allCems -> _allCemeteries.postValue(Resource.success(allCems)) }




        }
    }

    fun getCemsList() : LiveData<Resource<List<CemeteryDomain>>>{
        return allCemeteries
    }

}