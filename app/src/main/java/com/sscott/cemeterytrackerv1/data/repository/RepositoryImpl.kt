package com.sscott.cemeterytrackerv1.data.repository

import android.content.Context
import com.sscott.cemeterytrackerv1.data.local.datasource.LocalDataSource
import com.sscott.cemeterytrackerv1.data.models.domain.Sync
import com.sscott.cemeterytrackerv1.data.models.entities.CemeteryGraves
import com.sscott.cemeterytrackerv1.data.models.network.*
import com.sscott.cemeterytrackerv1.data.remote.datasource.RemoteDataSource
import com.sscott.cemeterytrackerv1.other.Resource
import com.sscott.cemeterytrackerv1.other.ResponseHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.Exception


class RepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val responseHandler: ResponseHandler,
    context : Context
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
            responseHandler.handleSuccess(remoteDataSource.allCemeteries().asDomainModels())
        }catch (e :Exception){
            responseHandler.handleException(e)
        }
    }

    /*
        on a failure of adding cemetery to server, insert cemetery into local db to be sent to network through work manager

     */
    override suspend fun sendCemToNetwork(cemeteryDto: CemeteryDto) = withContext(Dispatchers.IO){
        try {
            responseHandler.handleSuccess(remoteDataSource.sendCemToNetwork(cemeteryDto))
        }catch (e : Exception){
            localDataSource.insertCemetery(cemeteryDto.asDatabaseModel())
            responseHandler.handleException(e)
        }
    }

    override suspend fun getMostRecentTimes(): Sync {
        return Sync(
            mostRecentLocalInsert = localDataSource.getMostRecentLocalInsert(),
            mostRecentServerInsert = remoteDataSource.getMostRecentServerInsert()
        )
    }

    override suspend fun unSyncedCemeteries(mostRecentServerInsert: Long): List<CemeteryGraves> {
        return localDataSource.unSyncedCemeteries(mostRecentServerInsert)
    }

    override suspend fun sendUnsyncedCemeteries(unsyncedCemeteries: List<CemeteryDto>): Resource<List<CemeteryDto>> {
        return try {
            responseHandler.handleSuccess(remoteDataSource.sendUnsyncedCemeteries(unsyncedCemeteries))
        }catch (e : Exception){
            responseHandler.handleException(e)
        }
    }
}