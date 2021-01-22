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

    override suspend fun allCemeteries() = withContext(Dispatchers.IO){
        try {
            responseHandler.handleSuccess(mapper.cemDto.toDomainList(remoteDataSource.allCemeteries()))
        }catch (e :Exception){
            Timber.i("Error for allCemeteries() call $e")
            responseHandler.handleException(e)
        }
    }



        override suspend fun myCemeteries(userName : String) = withContext(Dispatchers.IO){
        try {
            responseHandler.handleSuccess(
                    mapper.cemDto.toDomainList(remoteDataSource.myCemeteries(userName))
            )
        }catch (e :Exception){
            responseHandler.handleException(e)
        }
    }

    override fun getCemsFromSearch(searchQuery: String): Flow<List<CemeteryDomain>> {
        return localDataSource.getCemsFromSearch(searchQuery)
                .map { mapper.cem.toDomainList(it) }
    }

    /*
               Client generates random UUID for cemeteryId inserts into local db
               Then sends to network.
               Local db is used to display users my cemeteries list

             */
    override suspend fun sendCemToNetwork(cemetery: CemeteryDomain) = withContext(Dispatchers.IO){
        try {
            responseHandler.handleSuccess(remoteDataSource.sendCemToNetwork(mapper.cemDto.fromDomain(cemetery)))
        }catch (e : Exception){
            responseHandler.handleException(e)
        }
    }

    override suspend fun insertCemetery(cemetery: CemeteryDomain): Long {
        return localDataSource.insertCemetery(mapper.cem.fromDomain(cemetery))
    }

    override suspend fun getCemetery(cemId: Long): CemeteryDomain {
        return mapper.cem.toDomain(localDataSource.getCemetery(cemId))
    }

    override suspend fun getNetworkCemetery(id: Long) : Resource<CemeteryDomain> = withContext(Dispatchers.IO){
        try {
            val response = remoteDataSource.getCemetery(id)
            responseHandler.handleSuccess(mapper.cemDto.toDomain(response))
        }catch (e : Exception){
            Timber.i(e)
            responseHandler.handleException(e)
        }


    }

    override suspend fun insertGrave(grave: GraveDomain): Long {
        return localDataSource.insertGrave(mapper.grave.fromDomain(grave))
    }

    override suspend fun sendGraveToNetwork(grave : GraveDomain) = withContext(Dispatchers.IO) {
        try {

            val graveResponse = remoteDataSource.sendGraveToNetwork(mapper.graveDto.fromDomain(grave))

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

    override suspend fun unSyncedCemeteries(mostRecentServerInsert: Long): List<CemeteryDomain> {

        val unsyncedCems = localDataSource.unSyncedCemeteries(mostRecentServerInsert)
        return mapper.cem.toCemDomainList(unsyncedCems)
    }

    override suspend fun sendUnsyncedCemeteries(unsyncedCemeteries: List<CemeteryDomain>): Resource<List<CemeteryDomain>> {
        return try {

            val cemListResponse = remoteDataSource.sendUnsyncedCemeteries( mapper.cemDto.toNetworkList(unsyncedCemeteries))
            responseHandler.handleSuccess(mapper.cemDto.toDomainList(cemListResponse))
        }catch (e : Exception){
            Timber.i("sendUnSyncedCems() repo function error occured $e")
            responseHandler.handleException(e)
        }
    }
}