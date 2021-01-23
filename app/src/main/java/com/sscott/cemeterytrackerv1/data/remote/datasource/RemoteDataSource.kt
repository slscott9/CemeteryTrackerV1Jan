package com.sscott.cemeterytrackerv1.data.remote.datasource


import com.sscott.cemeterytrackerv1.data.models.domain.GraveDomain
import com.sscott.cemeterytrackerv1.data.models.network.UserDto
import com.sscott.cemeterytrackerv1.data.models.network.cemdto.CemeteryDto
import com.sscott.cemeterytrackerv1.data.models.network.gravedto.GraveDto
import com.sscott.cemeterytrackerv1.other.Resource
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface RemoteDataSource {

    //Authentication

    suspend fun login(userDto: UserDto) : UserDto

    suspend fun register(userDto: UserDto) : UserDto

    //Cemetery

    suspend fun allCemeteries() : List<CemeteryDto>

    suspend fun myCemeteries(userName : String) : List<CemeteryDto>

    suspend fun getCemetery(id : Long) : CemeteryDto

    suspend fun sendCem(cemeteryDto: CemeteryDto) : CemeteryDto

    suspend fun sendCemList(cemList : List<CemeteryDto>) : List<CemeteryDto>

    suspend fun searchCemeteries(query : String) : List<CemeteryDto>




    //Grave

    suspend fun getMostRecentServerInsert() : Long

    suspend fun sendGraveList(graveList : List<GraveDto>) : List<GraveDto>

    suspend fun sendGrave(graveDto : GraveDto) : GraveDto



}