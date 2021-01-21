package com.sscott.cemeterytrackerv1.data.local.datasource

import androidx.lifecycle.LiveData
import com.sscott.cemeterytrackerv1.data.models.entities.cemetery.Cemetery
import com.sscott.cemeterytrackerv1.data.models.entities.cemetery.CemeteryGraves
import com.sscott.cemeterytrackerv1.data.models.entities.grave.Grave

interface LocalDataSource {

    suspend fun insertCemetery(cemetery: Cemetery) : Long

    suspend fun getMostRecentLocalInsert() : Long

    suspend fun unSyncedCemeteries(mostRecentServerInsert : Long) : List<CemeteryGraves>

    suspend fun insertGrave(grave: Grave) : Long

    fun getCemetery(cemId : Long) : LiveData<Cemetery>
}