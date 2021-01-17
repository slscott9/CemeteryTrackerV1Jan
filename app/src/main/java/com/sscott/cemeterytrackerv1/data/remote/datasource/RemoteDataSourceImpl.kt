package com.sscott.cemeterytrackerv1.data.remote.datasource

import com.sscott.cemeterytrackerv1.data.models.network.CemeteryDto
import com.sscott.cemeterytrackerv1.data.models.network.UserDto
import com.sscott.cemeterytrackerv1.other.Resource
import com.sscott.cemeterytrackerv1.other.ResponseHandler
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

    override suspend fun sendCemToNetwork(cemeteryDto: CemeteryDto): CemeteryDto {
        return sendCemToNetwork(cemeteryDto)
    }
}