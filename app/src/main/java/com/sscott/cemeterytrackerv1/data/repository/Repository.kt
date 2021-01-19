package com.sscott.cemeterytrackerv1.data.repository

import com.sscott.cemeterytrackerv1.data.models.domain.CemeteryDomain
import com.sscott.cemeterytrackerv1.data.models.domain.Sync
import com.sscott.cemeterytrackerv1.data.models.entities.CemeteryGraves
import com.sscott.cemeterytrackerv1.data.models.network.CemeteryDto
import com.sscott.cemeterytrackerv1.data.models.network.UserDto
import com.sscott.cemeterytrackerv1.other.Resource

interface Repository {

    //authentication
    suspend fun login(userDto: UserDto): Resource<UserDto>

    suspend fun register(userDto: UserDto): Resource<UserDto>

    //insert and get cems
    suspend fun allCemeteries(): Resource<List<CemeteryDomain>>

    suspend fun sendCemToNetwork(cemeteryDto: CemeteryDto): Resource<CemeteryDto>


    //Sync cemeteries and graves
    suspend fun getMostRecentTimes(): Sync

    suspend fun unSyncedCemeteries(mostRecentServerInsert: Long): List<CemeteryGraves>

    suspend fun sendUnsyncedCemeteries(unsyncedCemeteries : List<CemeteryDto>) : Resource<List<CemeteryDto>>


}