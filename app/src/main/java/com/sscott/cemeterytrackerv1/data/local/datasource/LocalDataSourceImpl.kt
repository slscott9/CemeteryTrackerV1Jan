package com.sscott.cemeterytrackerv1.data.local.datasource

import androidx.lifecycle.LiveData
import com.sscott.cemeterytrackerv1.data.models.entities.cemetery.Cemetery
import com.sscott.cemeterytrackerv1.data.models.entities.cemetery.CemeteryGraves
import com.sscott.cemeterytrackerv1.data.models.entities.grave.Grave
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val dao: CemeteryDao,
) : LocalDataSource {

    override suspend fun insertCemetery(cemetery: Cemetery) : Long{
        return dao.insertCemetery(cemetery)
    }

    override suspend fun getMostRecentLocalInsert(): Long {
        return dao.getMostRecentLocalInsert()
    }

    override suspend fun unSyncedCemeteries(mostRecentServerInsert: Long): List<CemeteryGraves> {
        return dao.unSyncedCemeteries(mostRecentServerInsert)
    }

    override suspend fun insertGrave(grave: Grave): Long {
        return dao.insertGrave(grave)
    }

    override suspend fun getCemetery(cemId: Long): Cemetery {
        return dao.getCemetery(cemId)
    }
}