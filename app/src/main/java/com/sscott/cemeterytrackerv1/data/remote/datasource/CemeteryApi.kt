package com.sscott.cemeterytrackerv1.data.remote.datasource


import com.sscott.cemeterytrackerv1.data.models.network.UserDto
import com.sscott.cemeterytrackerv1.data.models.network.cemdto.CemeteryDto
import com.sscott.cemeterytrackerv1.data.models.network.gravedto.GraveDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CemeteryApi {

    @POST("/api/auth/v1/login")
    suspend fun login(@Body userDto : UserDto) : UserDto

    @POST("/api/v1/register")
    suspend fun register(@Body userDto: UserDto) : UserDto

    @GET("/api/v1/cemeteries")
    suspend fun allCemeteries() : List<CemeteryDto>

    @GET("/api/v1/cemeteries/{userName}")
    suspend fun myCemeteries(@Path("userName") userName : String) : List<CemeteryDto>

    @POST("/api/v1/cemetery")
    suspend fun sendCemToNetwork(@Body cemeteryDto: CemeteryDto) : CemeteryDto

    @GET("/api/v1/cemetery/sync")
    suspend fun getMostRecentServerInsert() : Long

    @POST("/api/v1/cemeteries/add")
    suspend fun sendUnsyncedCemeteries(@Body unsyncedCemeteries : List<CemeteryDto>) : List<CemeteryDto>

    @POST("/api/v1/grave")
    suspend fun sendGraveToNetwork(@Body graveDto: GraveDto) : GraveDto





}