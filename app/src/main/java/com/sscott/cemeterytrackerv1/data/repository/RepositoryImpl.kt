package com.sscott.cemeterytrackerv1.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.sscott.cemeterytrackerv1.data.local.datasource.LocalDataSource
import com.sscott.cemeterytrackerv1.data.models.ModelMapper
import com.sscott.cemeterytrackerv1.data.models.domain.CemeteryDomain
import com.sscott.cemeterytrackerv1.data.models.domain.GraveDomain
import com.sscott.cemeterytrackerv1.data.models.domain.Sync
import com.sscott.cemeterytrackerv1.data.models.entities.cemetery.Cemetery
import com.sscott.cemeterytrackerv1.data.models.entities.grave.Grave
import com.sscott.cemeterytrackerv1.data.models.network.*
import com.sscott.cemeterytrackerv1.data.remote.datasource.RemoteDataSource
import com.sscott.cemeterytrackerv1.other.Resource
import com.sscott.cemeterytrackerv1.other.ResponseHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import kotlin.Exception

//should return domain models!!

class RepositoryImpl  (
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val responseHandler: ResponseHandler,
    context : Context,
)  : Repository, ModelMapper() {

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
            responseHandler.handleSuccess(cemeteryDtoMapper.mapToDomainModelList(remoteDataSource.allCemeteries()))
        }catch (e :Exception){
            Timber.i("Error for allCemeteries() call $e")
            responseHandler.handleException(e)
        }
    }



        override suspend fun myCemeteries(userName : String) = withContext(Dispatchers.IO){
        try {
            responseHandler.handleSuccess(
                    cemeteryDtoMapper.mapToDomainModelList(remoteDataSource.myCemeteries(userName))
            )
        }catch (e :Exception){
            responseHandler.handleException(e)
        }
    }

    /*
            on a failure of adding cemetery to server, insert cemetery into local db to be sent to network through work manager

         */
    override suspend fun sendCemToNetwork(cemetery: CemeteryDomain) = withContext(Dispatchers.IO){
        try {
            responseHandler.handleSuccess(remoteDataSource.sendCemToNetwork(cemeteryDtoMapper.mapFromDomainModel(cemetery)))
        }catch (e : Exception){
            responseHandler.handleException(e)
        }
    }

    override suspend fun insertCemetery(cemetery: CemeteryDomain): Long {
        return localDataSource.insertCemetery(cemeteryMapper.mapFromDomainModel(cemetery))
    }

    override fun getCemetery(cemId: Long): LiveData<CemeteryDomain> {
        return liveData {
            localDataSource.getCemetery(cemId)
        }
    }

    override suspend fun insertGrave(grave: GraveDomain): Long {
        return localDataSource.insertGrave(graveMapper.mapFromDomainModel(grave))
    }

    override suspend fun sendGraveToNetwork(grave : GraveDomain) = withContext(Dispatchers.IO) {
        try {

            val graveResponse = remoteDataSource.sendGraveToNetwork(graveDtoMapper.mapFromDomainModel(grave))

            responseHandler.handleSuccess(graveDtoMapper.mapToDomainModel(graveResponse))


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
        return cemeteryMapper.cemGravesToDomain(unsyncedCems)
    }

    override suspend fun sendUnsyncedCemeteries(unsyncedCemeteries: List<CemeteryDomain>): Resource<List<CemeteryDomain>> {
        return try {

            val cemListResponse = remoteDataSource.sendUnsyncedCemeteries(cemeteryDtoMapper.toNetworkList(unsyncedCemeteries))
            responseHandler.handleSuccess(cemeteryDtoMapper.mapToDomainModelList(cemListResponse))
        }catch (e : Exception){
            Timber.i("sendUnSyncedCems() repo function error occured $e")
            responseHandler.handleException(e)
        }
    }
}