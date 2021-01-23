package com.sscott.cemeterytrackerv1.data.local.datasource

import androidx.lifecycle.LiveData
import com.sscott.cemeterytrackerv1.data.models.entities.cemetery.Cemetery
import com.sscott.cemeterytrackerv1.data.models.entities.cemetery.CemeteryGraves
import com.sscott.cemeterytrackerv1.data.models.entities.grave.Grave
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val dao: CemeteryDao,
) : LocalDataSource {

    override suspend fun insertCemetery(cemetery: Cemetery) : Long{
        return dao.insertCemetery(cemetery)
    }

    override suspend fun updateCemetery(cemetery: Cemetery) {
        return dao.updateCemetery(cemetery)
    }

    override suspend fun updateGrave(grave: Grave) {
        return dao.updateGrave(grave)
    }

    override suspend fun getMostRecentLocalInsert(): Long {
        return dao.getMostRecentLocalInsert()
    }

    override suspend fun unSyncedCemeteries(isSynced: Boolean): List<CemeteryGraves> {
        return dao.unsyncedCemeteries(isSynced)
    }

    override suspend fun unSyncedGraves(isSynced: Boolean): List<Grave> {
        return dao.unsyncedGraves(isSynced)
    }

    override suspend fun insertGrave(grave: Grave): Long {
        return dao.insertGrave(grave)
    }

    override suspend fun getCemetery(cemId: Long): Cemetery {
        return dao.getCemetery(cemId)
    }

    override fun getCemsFromSearch(searchQuery: String): Flow<List<Cemetery>> {
        return dao.getCemsFromSearch(searchQuery)
    }

    override fun allCemeteries(): Flow<List<Cemetery>> {
        return dao.allCemeteries()
    }
}