package com.sscott.cemeterytrackerv1.data.local.datasource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.sscott.cemeterytrackerv1.data.models.entities.Cemetery
import com.sscott.cemeterytrackerv1.data.models.entities.CemeteryGraves

@Dao
interface CemeteryDao {

    @Insert
    suspend fun insertCemetery(cemetery : Cemetery) : Long

    @Query("select max(epochTimeAdded) from cemetery_v1_table")
    suspend fun getMostRecentLocalInsert() : Long

    @Transaction
    @Query("select * from cemetery_v1_table where epochTimeAdded > :mostRecentServerInsert")
    suspend fun unSyncedCemeteries(mostRecentServerInsert : Long) : List<CemeteryGraves>
}