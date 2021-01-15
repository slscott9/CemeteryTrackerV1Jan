package com.sscott.cemeterytrackerv1.data.repository

import com.sscott.cemeterytrackerv1.data.models.network.UserDto
import com.sscott.cemeterytrackerv1.other.Resource

interface Repository {

    suspend fun login(userDto: UserDto) : Resource<UserDto>

    suspend fun register(userDto: UserDto) : Resource<UserDto>

}