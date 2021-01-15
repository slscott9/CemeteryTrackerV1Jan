package com.sscott.cemeterytrackerv1.data.local.datasource

import com.sscott.cemeterytrackerv1.other.ResponseHandler
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val dao: CemeteryDao,
) : LocalDataSource {
}