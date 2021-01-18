package com.sscott.cemeterytrackerv1.data.repository

import com.sscott.cemeterytrackerv1.data.models.domain.CemeteryDomain
import com.sscott.cemeterytrackerv1.data.models.domain.Sync
import com.sscott.cemeterytrackerv1.data.models.network.CemeteryDto
import com.sscott.cemeterytrackerv1.data.models.network.UserDto
import com.sscott.cemeterytrackerv1.other.Resource

interface Repository {

    suspend fun login(userDto: UserDto) : Resource<UserDto>

    suspend fun register(userDto: UserDto) : Resource<UserDto>

    suspend fun allCemeteries() : Resource<List<CemeteryDomain>>

    suspend fun sendCemToNetwork(cemeteryDto: CemeteryDto) : Resource<CemeteryDto>

    suspend fun getMostRecentTimes() : Sync





}