package com.sscott.cemeterytrackerv1.data.remote.datasource


import com.sscott.cemeterytrackerv1.data.models.network.UserDto
import com.sscott.cemeterytrackerv1.data.models.network.cemdto.CemeteryDto
import com.sscott.cemeterytrackerv1.data.models.network.gravedto.GraveDto
import kotlinx.coroutines.flow.Flow
import retrofit2.http.*

interface CemeteryApi {

    //Authentication

    @POST("/api/auth/v1/login")
    suspend fun login(@Body userDto : UserDto) : UserDto

    @POST("/api/v1/register")
    suspend fun register(@Body userDto: UserDto) : UserDto


    //Cemetery

    @GET("/api/v1/cemeteries")
    suspend fun allCemeteries() : List<CemeteryDto>

    @GET("/api/v1/cemeteries/{userName}")
    suspend fun myCemeteries(@Path("userName") userName : String) : List<CemeteryDto>

    @POST("/api/v1/cemetery")
    suspend fun sendCem(@Body cemeteryDto: CemeteryDto) : CemeteryDto

    @GET("/api/v1/cemetery/{id}")
    suspend fun getCemetery(@Path("id") id : Long) : CemeteryDto

    @POST("/api/v1/cemeteries/add")
    suspend fun sendCemList(@Body cemList : List<CemeteryDto>) : List<CemeteryDto>

    @GET("/api/v1/cemetery/search/{query}")
    suspend fun searchCemeteries(@Path("query") query : String) : List<CemeteryDto>



    //Grave

    @GET("/api/v1/cemetery/sync")
    suspend fun getMostRecentServerInsert() : Long

    @POST("/api/v1/graves/add")
    suspend fun sendGraveList(@Body graveList : List<GraveDto>) : List<GraveDto>

    @POST("/api/v1/grave")
    suspend fun sendGrave(@Body graveDto: GraveDto) : GraveDto







}