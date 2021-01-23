package com.sscott.cemeterytrackerv1.data.repository

import androidx.lifecycle.LiveData
import com.sscott.cemeterytrackerv1.data.models.domain.CemeteryDomain
import com.sscott.cemeterytrackerv1.data.models.domain.GraveDomain
import com.sscott.cemeterytrackerv1.data.models.domain.Sync
import com.sscott.cemeterytrackerv1.data.models.entities.cemetery.Cemetery
import com.sscott.cemeterytrackerv1.data.models.entities.grave.Grave
import com.sscott.cemeterytrackerv1.data.models.network.UserDto
import com.sscott.cemeterytrackerv1.data.models.network.cemdto.CemeteryDto
import com.sscott.cemeterytrackerv1.data.models.network.gravedto.GraveDto
import com.sscott.cemeterytrackerv1.other.Resource
import kotlinx.coroutines.flow.Flow

interface Repository {

    //authentication
    suspend fun login(userDto: UserDto): Resource<UserDto>

    suspend fun register(userDto: UserDto): Resource<UserDto>

    //Cemetery Network

    fun allNetworkCems(): Flow<List<CemeteryDomain>>

    fun myNetworkCems(userName : String): Flow<List<CemeteryDomain>>

    suspend fun searchNetworkCems(query : String) : Flow<List<CemeteryDomain>>

    suspend fun sendCem(cemetery: CemeteryDomain): Resource<CemeteryDto>

    suspend fun sendCemList(cemList : List<CemeteryDomain>) : Resource<List<CemeteryDomain>>

    suspend fun getNetworkCem(id : Long) : Resource<CemeteryDomain>




    //Cemetery Local

    fun allLocalCems() : Flow<List<CemeteryDomain>>

    fun searchLocalCems(searchQuery: String) : Flow<List<CemeteryDomain>>

    suspend fun insertCemetery(cemetery: CemeteryDomain) : Long

    suspend fun updateCemetery(cemetery: CemeteryDomain)

    suspend fun getLocalCem(cemId : Long) : CemeteryDomain

    suspend fun unSyncedCemeteries(isSynced : Boolean): List<CemeteryDomain>



    //Grave Network

    suspend fun sendGrave(grave : GraveDomain) : Resource<GraveDomain>

    suspend fun sendGraveList(graveList : List<GraveDomain>) : Resource<List<GraveDomain>>



    //Grave Local

    suspend fun insertGrave(grave: GraveDomain) : Long

    suspend fun updateGrave(grave: GraveDomain)

    suspend fun unSyncedGraves(isSynced: Boolean) : List<GraveDomain>



    suspend fun getMostRecentTimes(): Sync







}