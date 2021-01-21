package com.sscott.cemeterytrackerv1.data.local.datasource

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.sscott.cemeterytrackerv1.data.models.entities.cemetery.Cemetery
import com.sscott.cemeterytrackerv1.data.models.entities.cemetery.CemeteryGraves
import com.sscott.cemeterytrackerv1.data.models.entities.grave.Grave

@Dao
interface CemeteryDao {

    @Insert
    suspend fun insertCemetery(cemetery : Cemetery) : Long

    @Query("select max(epochTimeAdded) from cemetery_v1_table")
    suspend fun getMostRecentLocalInsert() : Long

    @Transaction
    @Query("select * from cemetery_v1_table where epochTimeAdded > :mostRecentServerInsert")
    suspend fun unSyncedCemeteries(mostRecentServerInsert : Long) : List<CemeteryGraves>

    @Insert
    suspend fun insertGrave(grave: Grave) : Long

    @Query("select * from cemetery_v1_table where cemeteryId = :cemId")
    fun getCemetery(cemId : Long) : LiveData<Cemetery>

}