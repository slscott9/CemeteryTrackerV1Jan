package com.sscott.cemeterytrackerv1.worker

import android.content.Context
import androidx.hilt.Assisted
import androidx.hilt.work.WorkerInject
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.sscott.cemeterytrackerv1.data.repository.Repository
import timber.log.Timber

class CemeteryRefreshWorker @WorkerInject constructor(
        @Assisted workerParameters: WorkerParameters,
        @Assisted context: Context,
        private val repository: Repository
) : CoroutineWorker(context, workerParameters) {

    //default is Dispatchers.Default

    /*

     */

    companion object {
        const val CEMETERY_WORKER = "CEM_REFRESH_WORKER_V1"
    }

    override suspend fun doWork(): Result {
        Timber.i("doWork() called")
        return try {

            val unSyncedCemeteries = repository.unSyncedCemeteries(false)
            if(unSyncedCemeteries.isNotEmpty()){
                Timber.i("Unsynced cems is not empty sending to network ${unSyncedCemeteries}")
                repository.sendCemList(unSyncedCemeteries)

                unSyncedCemeteries.forEach {
                    repository.updateCemetery(it.copy(isSynced = true))
                }
            }

            val unsyncedGraves = repository.unSyncedGraves(false)
            if(unsyncedGraves.isNotEmpty()){
                Timber.i("Unsynced graves is not empty sending to network ${unsyncedGraves}")
                repository.sendGraveList(unsyncedGraves)

                unsyncedGraves.forEach {
                    repository.updateGrave(it.copy(isSynced = true))
                }
            }

            Result.success()
        } catch (e: Exception) {
            Timber.i("Work failed $e")
            Result.retry()
        }

//        Timber.i("Work is finished returning Result.success()")
    }
}