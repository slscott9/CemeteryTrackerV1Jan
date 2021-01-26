package com.sscott.cemeterytrackerv1.location

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.location.Location
import android.os.Binder
import android.os.IBinder
import android.os.Looper
import android.provider.ContactsContract.Directory.PACKAGE_NAME
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.gms.location.*
import timber.log.Timber
import java.util.concurrent.TimeUnit

const val TAG = "LocationService"

class BoundLocationService : Service()  {

    private var configurationChange = false

    private var isBound = false

    private val localBinder = LocalBinder()

    private lateinit var fusedLocationProviderClient : FusedLocationProviderClient

    private lateinit var locationRequest: LocationRequest

    private lateinit var locationCallback: LocationCallback

    private var currentLocation : Location? = null

    override fun onCreate() {
        super.onCreate()

        Timber.d("LocationService onCreate() called")
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        locationRequest = LocationRequest().apply {

            interval = TimeUnit.SECONDS.toMillis(60)
            fastestInterval = TimeUnit.SECONDS.toMillis(10)
            maxWaitTime = TimeUnit.MINUTES.toMillis(1)
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                super.onLocationResult(locationResult)

                if (locationResult?.lastLocation != null) {

                    currentLocation = locationResult.lastLocation
                    val intent = Intent(ACTION_BOUND_LOCATION_BROADCAST)
                    intent.putExtra(EXTRA_LOCATION, currentLocation)
                    LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(intent)
                } else {
                    Log.d(TAG, "location missing in callback")
                }
            }
        }
    }

    //onBind() called from AddCemFragment - When using onBind() onStartCommand is not called - must manually unbind the service

    @SuppressLint("MissingPermission")
    fun subscribeToLocationUpdates() {
        startService(Intent(applicationContext, BoundLocationService::class.java))
        try{
            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest, locationCallback, Looper.myLooper()
            )
        }catch (e :SecurityException){
            Log.e(TAG, "Lost location permissions, Could not remove updates. $e")
        }
    }

    fun unsubscribeToLocationUpdates() {
        try {
            val removeTask = fusedLocationProviderClient.removeLocationUpdates(locationCallback)
            removeTask.addOnCompleteListener { task ->
                if(task.isSuccessful){
                    Log.d(TAG, "Location Callback removed")
                    stopSelf()
                }else{
                    Timber.i("Failed to remove location callback")
                }
            }
        }catch (e : SecurityException){
            Log.e(TAG,"Lost location permissions could not remove update $e")
        }
    }


    override fun onBind(p0: Intent?): IBinder? {
        stopForeground(true)
        isBound = false
        configurationChange = false
        return localBinder

    }

    override fun onRebind(intent: Intent) {
        Log.d(TAG, "onRebind()")

        // MainActivity (client) returns to the foreground and rebinds to service, so the service
        // can become a background services.
        stopForeground(true)
        isBound = false
        configurationChange = false
        super.onRebind(intent)
    }

    override fun onUnbind(intent: Intent?): Boolean {

        if(!configurationChange){
            isBound = true
        }
        return true
    }




    inner class LocalBinder : Binder() {
        internal val service: BoundLocationService
            get() = this@BoundLocationService
    }

    companion object {
        internal const val ACTION_BOUND_LOCATION_BROADCAST =
            "$PACKAGE_NAME.action.ACTION_BOUND_LOCATION_BROADCAST"

        internal const val EXTRA_LOCATION = "$PACKAGE_NAME.extra.LOCATION"

    }
}