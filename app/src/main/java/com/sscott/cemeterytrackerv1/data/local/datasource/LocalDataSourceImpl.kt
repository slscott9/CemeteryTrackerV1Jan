package com.sscott.cemeterytrackerv1.data.local.datasource

import com.sscott.cemeterytrackerv1.data.models.entities.Cemetery
import com.sscott.cemeterytrackerv1.data.models.entities.CemeteryGraves
import com.sscott.cemeterytrackerv1.data.models.network.CemeteryDto
import com.sscott.cemeterytrackerv1.other.ResponseHandler
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
        TODO("Not yet implemented")
    }
}