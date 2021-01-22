package com.sscott.cemeterytrackerv1.data.repository

import androidx.lifecycle.LiveData
import com.sscott.cemeterytrackerv1.data.models.domain.CemeteryDomain
import com.sscott.cemeterytrackerv1.data.models.domain.GraveDomain
import com.sscott.cemeterytrackerv1.data.models.domain.Sync
import com.sscott.cemeterytrackerv1.data.models.entities.cemetery.Cemetery
import com.sscott.cemeterytrackerv1.data.models.entities.grave.Grave
import com.sscott.cemeterytrackerv1.data.models.network.UserDto
import com.sscott.cemeterytrackerv1.data.models.network.cemdto.CemeteryDto
import com.sscott.cemeterytrackerv1.other.Resource
import kotlinx.coroutines.flow.Flow

interface Repository {

    //authentication
    suspend fun login(userDto: UserDto): Resource<UserDto>

    suspend fun register(userDto: UserDto): Resource<UserDto>

    //insert and get cems
    fun allCemeteries(): Flow<List<CemeteryDomain>>

    fun myCemeteries(userName : String): Flow<List<CemeteryDomain>>

    suspend fun sendCemToNetwork(cemetery: CemeteryDomain): Resource<CemeteryDto>

    suspend fun insertCemetery(cemetery: CemeteryDomain) : Long

    suspend fun getCemetery(cemId : Long) : CemeteryDomain

    suspend fun getNetworkCemetery(id : Long) : Resource<CemeteryDomain>

    fun getCemsFromSearch(searchQuery: String) : Flow<List<CemeteryDomain>>



    //insert and get database graves
    suspend fun insertGrave(grave: GraveDomain) : Long


    //Sync cemeteries and graves
    suspend fun getMostRecentTimes(): Sync

    suspend fun unSyncedCemeteries(mostRecentServerInsert: Long): List<CemeteryDomain>

    suspend fun sendUnsyncedCemeteries(unsyncedCemeteries : List<CemeteryDomain>) : Resource<List<CemeteryDomain>>

    suspend fun sendGraveToNetwork(grave : GraveDomain) : Resource<GraveDomain>



}