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

    //Authentication

    override suspend fun login(userDto: UserDto): UserDto {
        return cemeteryApi.login(userDto)
    }

    override suspend fun register(userDto: UserDto): UserDto {
        return cemeteryApi.register(userDto)
    }



    //Cemetery

    override suspend fun sendCemList(cemList: List<CemeteryDto>): List<CemeteryDto> {
        return cemeteryApi.sendCemList(cemList)
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

    override suspend fun sendCem(cemeteryDto: CemeteryDto): CemeteryDto {
        return cemeteryApi.sendCem(cemeteryDto)
    }



    //Grave
    override suspend fun sendGraveList(graveList: List<GraveDto>): List<GraveDto> {
        return cemeteryApi.sendGraveList(graveList)
    }

    override suspend fun sendGrave(graveDto: GraveDto): GraveDto {
        return cemeteryApi.sendGrave(graveDto)
    }



    //Search and sync

    override suspend fun getMostRecentServerInsert(): Long {
        return cemeteryApi.getMostRecentServerInsert()
    }

    override suspend fun searchCemeteries(query: String): List<CemeteryDto> {
        return cemeteryApi.searchCemeteries(query)
    }


}