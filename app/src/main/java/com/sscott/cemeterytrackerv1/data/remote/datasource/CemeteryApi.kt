package com.sscott.cemeterytrackerv1.data.remote.datasource

import com.sscott.cemeterytrackerv1.data.models.network.UserDto
import retrofit2.http.Body
import retrofit2.http.POST

interface CemeteryApi {

    @POST("/api/auth/v1/login")
    suspend fun login(@Body userDto : UserDto) : UserDto

    @POST("/api/v1/register")
    suspend fun register(@Body userDto: UserDto) : UserDto



}