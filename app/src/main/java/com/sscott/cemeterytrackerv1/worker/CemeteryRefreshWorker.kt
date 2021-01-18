package com.sscott.cemeterytrackerv1.worker

import android.content.Context
import androidx.hilt.Assisted
import androidx.hilt.work.WorkerInject
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.sscott.cemeterytrackerv1.data.remote.datasource.RemoteDataSource
import com.sscott.cemeterytrackerv1.data.repository.Repository

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
        try {

            val syncInfo = repository.getMostRecentTimes()

            if(syncInfo.mostRecentLocalInsert > syncInfo.mostRecentServerInsert){

            }




        }catch (e : Exception){
            return Result.retry()
        }
        return Result.success()
    }
}