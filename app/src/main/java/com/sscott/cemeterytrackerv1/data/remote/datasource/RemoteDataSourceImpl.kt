package com.sscott.cemeterytrackerv1.data.remote.datasource

import com.sscott.cemeterytrackerv1.data.models.network.UserDto
import com.sscott.cemeterytrackerv1.data.models.network.cemdto.CemeteryDto
import com.sscott.cemeterytrackerv1.data.models.network.gravedto.GraveDto
import com.sscott.cemeterytrackerv1.other.Resource
import com.sscott.cemeterytrackerv1.other.ResponseHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val cemeteryApi: CemeteryApi
) : RemoteDataSource {

    override suspend fun login(userDto: UserDto): UserDto {
        return cemeteryApi.login(userDto)
    }

    override suspend fun register(userDto: UserDto): UserDto {
        return cemeteryApi.register(userDto)
    }

    override suspend fun allCemeteries(): List<CemeteryDto> {
        return cemeteryApi.allCemeteries()
    }

    override suspend fun myCemeteries(userName : String): List<CemeteryDto> {
        return cemeteryApi.myCemeteries(userName)
    }

    override suspend fun getCemetery(id: Long): CemeteryDto {
        Timber.i("Cemetery id is $id")
        return  cemeteryApi.getCemetery(id)
    }

    override suspend fun sendCemToNetwork(cemeteryDto: CemeteryDto): CemeteryDto {
        return cemeteryApi.sendCemToNetwork(cemeteryDto)
    }

    override suspend fun getMostRecentServerInsert(): Long {
        return cemeteryApi.getMostRecentServerInsert()
    }

    override suspend fun sendUnsyncedCemeteries(unsyncedCemeteries: List<CemeteryDto>): List<CemeteryDto> {
        return cemeteryApi.sendUnsyncedCemeteries(unsyncedCemeteries)
    }

    override suspend fun sendGraveToNetwork(graveDto: GraveDto): GraveDto {
        return cemeteryApi.sendGraveToNetwork(graveDto)
    }
}