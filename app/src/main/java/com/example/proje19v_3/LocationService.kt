package com.example.proje19v_3

import android.content.Intent
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.SharedPreferences
import android.os.IBinder
import android.provider.ContactsContract.Data
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class LocationService: Service() {



    var location_lat = 0.0
    var location_lng = 0.0

    private  val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private lateinit var locationClient: LocationClient

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        locationClient = DefaultLocationClient(
            applicationContext,
            LocationServices.getFusedLocationProviderClient(applicationContext)
        )
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when(intent?.action){
            ACTION_START-> start()
            ACTION_STOP -> stop()
        }
        return super.onStartCommand(intent, flags, startId)
    }


    private fun start() {
        println("calısıtor")
        locationClient
            .getLocationUpdates(0)
            .catch {e -> e.printStackTrace()}
            .onEach { location ->

                SeferVariables.location_data[0]= location.latitude
                SeferVariables.location_data[1]= location.longitude
                println("otoAnonsTimer=${location.latitude}  otoAnonsState = ${location.latitude}")
                println( "fromcore = ${location.latitude} , ${location.longitude} ")
            }
            .launchIn(serviceScope)
    }



    private fun stop() {
        stopForeground(true)
        stopSelf()
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }

    companion object{
        const val ACTION_START = "ACTION_START"
        const val ACTION_STOP = "ACTION_STOP"
    }

}