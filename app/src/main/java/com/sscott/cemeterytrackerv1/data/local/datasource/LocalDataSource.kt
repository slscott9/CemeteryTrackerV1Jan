package com.sscott.cemeterytrackerv1.data.local.datasource

import androidx.lifecycle.LiveData
import com.sscott.cemeterytrackerv1.data.models.entities.cemetery.Cemetery
import com.sscott.cemeterytrackerv1.data.models.entities.cemetery.CemeteryGraves
import com.sscott.cemeterytrackerv1.data.models.entities.grave.Grave
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    suspend fun insertCemetery(cemetery: Cemetery) : Long

    suspend fun getMostRecentLocalInsert() : Long

    suspend fun unSyncedCemeteries(mostRecentServerInsert : Long) : List<CemeteryGraves>

    suspend fun insertGrave(grave: Grave) : Long

    suspend fun getCemetery(cemId : Long) : Cemetery

    fun getCemsFromSearch(searchQuery: String) : Flow<List<Cemetery>>

}