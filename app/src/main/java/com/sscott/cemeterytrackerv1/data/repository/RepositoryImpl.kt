package com.sscott.cemeterytrackerv1.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.sscott.cemeterytrackerv1.data.local.datasource.LocalDataSource
import com.sscott.cemeterytrackerv1.data.models.ModelMapperI
import com.sscott.cemeterytrackerv1.data.models.domain.CemeteryDomain
import com.sscott.cemeterytrackerv1.data.models.domain.GraveDomain
import com.sscott.cemeterytrackerv1.data.models.domain.Sync
import com.sscott.cemeterytrackerv1.data.models.entities.cemetery.Cemetery
import com.sscott.cemeterytrackerv1.data.models.entities.grave.Grave
import com.sscott.cemeterytrackerv1.data.models.network.*
import com.sscott.cemeterytrackerv1.data.models.network.cemdto.CemeteryDto
import com.sscott.cemeterytrackerv1.data.remote.datasource.RemoteDataSource
import com.sscott.cemeterytrackerv1.other.Resource
import com.sscott.cemeterytrackerv1.other.ResponseHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import timber.log.Timber
import kotlin.Exception

/*
    Repo is the Domain layer.

    Domain layer should not know about android stuff(live data)

    dao returns objects -> repo (converts to domain) -> view model (live data builder)

    Flow can be returned from the dao -> repo because it is kotlin specific not android
 */
class RepositoryImpl  (
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val responseHandler: ResponseHandler,
    context : Context,
    private val mapper: ModelMapperI
)  : Repository{

    //Authentication

    override suspend fun login(userDto: UserDto): Resource<UserDto> = withContext(Dispatchers.IO){
        try {
            responseHandler.handleSuccess(remoteDataSource.login(userDto))
        }catch (e : Exception){
            responseHandler.handleException(e)
        }
    }

    override suspend fun register(userDto: UserDto): Resource<UserDto> = withContext(Dispatchers.IO) {
        try {
            responseHandler.handleSuccess(remoteDataSource.register(userDto))
        }catch (e : Exception){
            responseHandler.handleException(e)
        }
    }



    //Cemetery Network

    override fun allNetworkCems(): Flow<List<CemeteryDomain>> {
        return flow{ emit(mapper.cemDto.toDomainList(remoteDataSource.allCemeteries())) }
    }

    override  fun myNetworkCems(userName : String): Flow<List<CemeteryDomain>> {
        return flow {
            emit(mapper.cemDto.toDomainList(remoteDataSource.myCemeteries(userName)))
        }
    }

    override suspend fun searchNetworkCems(query: String): Flow<List<CemeteryDomain>> {
        return flow { emit(mapper.cemDto.toDomainList(remoteDataSource.searchCemeteries(query)))}
    }



    override suspend fun sendCem(cemetery: CemeteryDomain) = withContext(Dispatchers.IO){
        try {
            responseHandler.handleSuccess(remoteDataSource.sendCem(mapper.cemDto.fromDomain(cemetery)))
        }catch (e : Exception){
            responseHandler.handleException(e)
        }
    }

    override suspend fun sendCemList(cemList: List<CemeteryDomain>): Resource<List<CemeteryDomain>> {
        return try {

            Timber.i("Sending unsynced cems to network ")
            val cemListResponse = remoteDataSource.sendCemList( mapper.cemDto.toNetworkList(cemList))
            responseHandler.handleSuccess(mapper.cemDto.toDomainList(cemListResponse))
        }catch (e : Exception){
            Timber.i("sendUnSyncedCems() repo function error occured $e")
            responseHandler.handleException(e)
        }
    }

    override suspend fun getNetworkCem(id: Long) : Resource<CemeteryDomain> = withContext(Dispatchers.IO){
        try {
            val response = remoteDataSource.getCemetery(id)
            responseHandler.handleSuccess(mapper.cemDto.toDomain(response))
        }catch (e : Exception){
            Timber.i(e)
            responseHandler.handleException(e)
        }
    }




    //Cemetery Local

    override fun allLocalCems(): Flow<List<CemeteryDomain>> {
        return localDataSource.allCemeteries().map { mapper.cem.toDomainList(it) }
    }


    override fun searchLocalCems(searchQuery: String): Flow<List<CemeteryDomain>> {
        return localDataSource.getCemsFromSearch(searchQuery)
            .map { mapper.cem.toDomainList(it) }
    }

    /*
               Client generates random UUID for cemeteryId inserts into local db
               Then sends to network.
               Local db is used to display users my cemeteries list

             */


    override suspend fun insertCemetery(cemetery: CemeteryDomain): Long {
        return localDataSource.insertCemetery(mapper.cem.fromDomain(cemetery))
    }

    override suspend fun updateCemetery(cemetery: CemeteryDomain) {
        localDataSource.updateCemetery(mapper.cem.fromDomain(cemetery))
    }

    override suspend fun getLocalCem(cemId: Long): CemeteryDomain {
        return mapper.cem.toDomain(localDataSource.getCemetery(cemId))
    }

    override suspend fun unSyncedCemeteries(isSynced : Boolean): List<CemeteryDomain> {

        val unsyncedCems = localDataSource.unSyncedCemeteries(isSynced)
        return mapper.cem.toCemDomainList(unsyncedCems)
    }




    //Grave Network

    override suspend fun sendGrave(grave : GraveDomain) = withContext(Dispatchers.IO) {
        try {
            val graveResponse = remoteDataSource.sendGrave(mapper.graveDto.fromDomain(grave))
            responseHandler.handleSuccess(mapper.graveDto.toDomain(graveResponse))
        }catch (e : Exception){
            responseHandler.handleException(e)
        }
    }

    override suspend fun getMostRecentTimes(): Sync {
        return Sync(
            mostRecentLocalInsert = localDataSource.getMostRecentLocalInsert(),
            mostRecentServerInsert = remoteDataSource.getMostRecentServerInsert()
        )
    }



    override suspend fun sendGraveList(graveList: List<GraveDomain>): Resource<List<GraveDomain>> {
        return try {
            val graveListResponse = remoteDataSource.sendGraveList(mapper.cemDto.graveDomainListToDto(graveList))
            responseHandler.handleSuccess(mapper.graveDto.toDomainList(graveListResponse))
        }catch (e : Exception){
            Timber.i(e)
            responseHandler.handleException(e)

        }
    }




   //Grave Local

    override suspend fun insertGrave(grave: GraveDomain): Long {
        return localDataSource.insertGrave(mapper.grave.fromDomain(grave))
    }

    override suspend fun updateGrave(grave: GraveDomain) {
        return localDataSource.updateGrave(mapper.grave.fromDomain(grave))
    }

    override suspend fun unSyncedGraves(isSynced: Boolean): List<GraveDomain> {
        return mapper.grave.toDomainList(localDataSource.unSyncedGraves(isSynced))
    }


}