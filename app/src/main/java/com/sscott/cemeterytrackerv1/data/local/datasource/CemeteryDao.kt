package com.sscott.cemeterytrackerv1.data.local.datasource

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sscott.cemeterytrackerv1.data.models.entities.cemetery.Cemetery
import com.sscott.cemeterytrackerv1.data.models.entities.cemetery.CemeteryGraves
import com.sscott.cemeterytrackerv1.data.models.entities.grave.Grave
import kotlinx.coroutines.flow.Flow

@Dao
interface CemeteryDao {

    @Insert
    suspend fun insertCemetery(cemetery : Cemetery) : Long

    @Update
    suspend fun updateCemetery(cemetery: Cemetery)

    @Update
    suspend fun updateGrave(grave: Grave)

    @Query("select max(epochTimeAdded) from cemetery_v1_table")
    suspend fun getMostRecentLocalInsert() : Long

//    @Transaction
//    @Query("select * from cemetery_v1_table where epochTimeAdded > :mostRecentServerInsert")
//    suspend fun unSyncedCemeteries(mostRecentServerInsert : Long) : List<CemeteryGraves>

    @Transaction
    @Query("select * from cemetery_v1_table where isSynced = :isSynced")
    suspend fun unsyncedCemeteries(isSynced: Boolean): List<CemeteryGraves>

    @Query("select * from grave_v1_table where isSynced = :isSynced")
    suspend fun unsyncedGraves(isSynced: Boolean) : List<Grave>

    @Insert
    suspend fun insertGrave(grave: Grave) : Long

    @Query("select * from cemetery_v1_table where cemeteryId = :cemId")
    suspend fun getCemetery(cemId : Long) : Cemetery

    //search cemeteries
    @Query("select * from cemetery_v1_table where name like '%' || :searchQuery || '%'")
    fun getCemsFromSearch(searchQuery: String) : Flow<List<Cemetery>>

    @Query("select * from cemetery_v1_table")
    fun allCemeteries() : Flow<List<Cemetery>>

}