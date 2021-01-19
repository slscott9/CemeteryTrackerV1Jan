package com.sscott.cemeterytrackerv1.data.remote.datasource

import com.sscott.cemeterytrackerv1.data.models.network.CemeteryDto
import com.sscott.cemeterytrackerv1.data.models.network.UserDto
import com.sscott.cemeterytrackerv1.other.Resource
import retrofit2.Response

interface RemoteDataSource {

    suspend fun login(userDto: UserDto) : UserDto

    suspend fun register(userDto: UserDto) : UserDto

    suspend fun allCemeteries() : List<CemeteryDto>

    suspend fun sendCemToNetwork(cemeteryDto: CemeteryDto) : CemeteryDto

    suspend fun getMostRecentServerInsert() : Long

    suspend fun sendUnsyncedCemeteries(unsyncedCemeteries : List<CemeteryDto>) : List<CemeteryDto>


}