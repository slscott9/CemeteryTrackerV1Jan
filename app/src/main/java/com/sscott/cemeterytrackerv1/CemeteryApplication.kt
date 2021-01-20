package com.sscott.cemeterytrackerv1

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.*
import com.sscott.cemeterytrackerv1.worker.CemeteryRefreshWorker
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class CemeteryApplication : Application() , Configuration.Provider{

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
        setupRecurringWork()
    }

    private fun setupRecurringWork() {
        Timber.i("Set up recurring work called")
        val constraints = Constraints.Builder()
            .setRequiresCharging(false)
            .build()

        val repeatingRequest = PeriodicWorkRequestBuilder<CemeteryRefreshWorker>(15, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            CemeteryRefreshWorker.CEMETERY_WORKER,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest
        )
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.VERBOSE)
            .setWorkerFactory(workerFactory)
            .build()
    }


}