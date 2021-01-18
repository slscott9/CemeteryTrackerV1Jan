package com.sscott.cemeterytrackerv1.data.local.datasource

import com.sscott.cemeterytrackerv1.data.models.entities.Cemetery
import com.sscott.cemeterytrackerv1.data.models.entities.CemeteryGraves
import com.sscott.cemeterytrackerv1.data.models.network.CemeteryDto

interface LocalDataSource {

    suspend fun insertCemetery(cemetery: Cemetery) : Long

    suspend fun getMostRecentLocalInsert() : Long

    suspend fun unSyncedCemeteries(mostRecentServerInsert : Long) : List<CemeteryGraves>

}