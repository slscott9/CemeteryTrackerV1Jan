package com.sscott.cemeterytrackerv1.worker

import android.content.Context
import androidx.hilt.Assisted
import androidx.hilt.work.WorkerInject
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.sscott.cemeterytrackerv1.data.models.network.asNetworkModelList
import com.sscott.cemeterytrackerv1.data.remote.datasource.RemoteDataSource
import com.sscott.cemeterytrackerv1.data.repository.Repository
import timber.log.Timber

class CemeteryRefreshWorker @WorkerInject constructor(
        @Assisted workerParameters: WorkerParameters,
        @Assisted context : Context,
        private val repository: Repository
): CoroutineWorker(context, workerParameters) {

    //default is Dispatchers.Default

    companion object {
        const val CEMETERY_WORKER = "CEMETERY_WORKER"
    }
    override suspend fun doWork(): Result {
        Timber.i("doWork() called")
        try {
            val syncInfo = repository.getMostRecentTimes()

            if(syncInfo.mostRecentLocalInsert > syncInfo.mostRecentServerInsert){
                Timber.i("mostRecentLocalInsert > mostRecentServerInsert")
                Timber.i("${syncInfo.mostRecentLocalInsert} > ${syncInfo.mostRecentServerInsert}")
                val unsyncedCemeteries = repository.unSyncedCemeteries(syncInfo.mostRecentServerInsert) //get all cemeteries/graves whose epoch time > mostRecentServerInsert

                repository.sendUnsyncedCemeteries(unsyncedCemeteries.asNetworkModelList())
            }
        }catch (e : Exception){
            Timber.i("Work failed $e")
            return Result.retry()
        }

        Timber.i("Work is finished returning Result.success()")
        return Result.success()
    }
}