package com.sscott.cemeterytrackerv1.data.repository

import com.sscott.cemeterytrackerv1.data.local.datasource.LocalDataSource
import com.sscott.cemeterytrackerv1.data.models.network.CemeteryDto
import com.sscott.cemeterytrackerv1.data.models.network.UserDto
import com.sscott.cemeterytrackerv1.data.models.network.asDomainModels
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
    private val responseHandler: ResponseHandler
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

    override suspend fun sendCemToNetwork(cemeteryDto: CemeteryDto) = withContext(Dispatchers.IO){
        try {
            responseHandler.handleSuccess(remoteDataSource.sendCemToNetwork(cemeteryDto))
        }catch (e : Exception){
            responseHandler.handleException(e)
        }
    }
}