package com.example.proje19v_3

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Button
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import com.example.proje19v_3.databinding.ActivityMainBinding

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.osmdroid.views.MapView

class MainActivity : AppCompatActivity() {

    private val handler = Handler(Looper.getMainLooper())

    private var state_buttonHome : Boolean = false
    private var state_buttonAnnouncement  : Boolean = false
    private var state_buttonSettings  : Boolean = false
    private lateinit var announcementPlayer: AnnouncementPlayer

    private lateinit var binding: ActivityMainBinding




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Intent(applicationContext, LocationService::class.java).apply {
            action = LocationService.ACTION_START
            startService(this)
        }
        replaceFragment(FragmentHome())
        announcementPlayer = AnnouncementPlayer(this)


        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        val windowInsetsController =
            WindowCompat.getInsetsController(window, window.decorView)

        // Configure the behavior of the hidden system bars.
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE





        val sharedPreferences: SharedPreferences = getSharedPreferences("sharedPrefs",Context.MODE_PRIVATE)
        var savedTime = sharedPreferences.getInt("time",20)
        var savedCount = sharedPreferences.getInt("count",3)
        var visMap = sharedPreferences.getBoolean("visMap",true)
        var visLoc = sharedPreferences.getBoolean("visLoc",true)
        var visVol = sharedPreferences.getBoolean("visVol",true)
        var volumePlayer = sharedPreferences.getFloat("vol",0.75f)
        SeferVariables.announcementDelayTime = savedTime
        SeferVariables.announcementRepeatNumber = savedCount
        SeferVariables.visiblity_map = visMap
        SeferVariables.visiblity_loc = visLoc
        SeferVariables.visiblity_vol = visVol
        SeferVariables.playerVolume = volumePlayer
        startLoop()
    }



    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer,fragment)
        fragmentTransaction.commit()
    }


    private fun startLoop() {
        handler.post(object : Runnable {
            override fun run() {

                val sharedPreferences: SharedPreferences = getSharedPreferences("sharedPrefs",Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.apply{
                    putInt("time",SeferVariables.announcementDelayTime)
                    putInt("count", SeferVariables.announcementRepeatNumber)
                    putBoolean("visMap",SeferVariables.visiblity_map)
                    putBoolean("visLoc",SeferVariables.visiblity_loc)
                    putBoolean("visVol",SeferVariables.visiblity_vol)
                    putFloat("vol",SeferVariables.playerVolume)
                }.apply()


                val home_button = findViewById<Button>(R.id.navigation_button1)
                val announcement_button = findViewById<Button>(R.id.navigation_button2)
                val settings_button = findViewById<Button>(R.id.navigation_button3)

                state_buttonHome = home_button.isPressed
                state_buttonAnnouncement = announcement_button.isPressed
                state_buttonSettings = settings_button.isPressed


                if (SeferVariables.fragment_state == 0) {
                    home_button.setBackgroundColor(resources.getColor(R.color.dentur_orange2))
                    announcement_button.setBackgroundColor(resources.getColor(R.color.dentur_orange))
                    settings_button.setBackgroundColor(resources.getColor(R.color.dentur_orange))
                }

                if (SeferVariables.fragment_state == 1) {
                    home_button.setBackgroundColor(resources.getColor(R.color.dentur_orange))
                    announcement_button.setBackgroundColor(resources.getColor(R.color.dentur_orange2))
                    settings_button.setBackgroundColor(resources.getColor(R.color.dentur_orange))
                }

                if (SeferVariables.fragment_state == 2) {
                    home_button.setBackgroundColor(resources.getColor(R.color.dentur_orange))
                    announcement_button.setBackgroundColor(resources.getColor(R.color.dentur_orange))
                    settings_button.setBackgroundColor(resources.getColor(R.color.dentur_orange2))
                }
                if (SeferVariables.fragment_state != 2 &&
                    SeferVariables.fragment_state != 1 &&
                    SeferVariables.fragment_state != 0 ) {
                    home_button.setBackgroundColor(resources.getColor(R.color.dentur_orange))
                    announcement_button.setBackgroundColor(resources.getColor(R.color.dentur_orange))
                    settings_button.setBackgroundColor(resources.getColor(R.color.dentur_orange))
                }



                if(state_buttonHome || SeferVariables.btn_home_state){
                    replaceFragment(FragmentHome())
                    SeferVariables.fragment_state = 0
                    SeferVariables.btn_home_state = false
                }
                if(state_buttonAnnouncement){
                    replaceFragment(FragmentAnnouncement())
                    SeferVariables.fragment_state = 1
                }
                if(state_buttonSettings){
                    replaceFragment(FragmentSettings())
                    SeferVariables.fragment_state = 2
                }
                if(SeferVariables.btn_seferSecim_state) {
                    replaceFragment(FragmentSeferSecim())
                    SeferVariables.fragment_state = 3
                    SeferVariables.btn_seferSecim_state = false
                }
                if(SeferVariables.btn_iskAnons_state) {
                    replaceFragment(FragmentIskAnons())
                    SeferVariables.fragment_state = 4
                    SeferVariables.btn_iskAnons_state = false
                }
                if(SeferVariables.btn_otoAnons_state) {
                    replaceFragment(FragmentOtoAnons())
                    SeferVariables.fragment_state = 5
                    SeferVariables.btn_otoAnons_state = false
                }
                if(SeferVariables.btn_gemiAnons_state){
                    replaceFragment(FragmentGemiAnons())
                    SeferVariables.fragment_state = 6
                    SeferVariables.btn_gemiAnons_state = false
                }





                GlobalScope.launch {

                    if(SeferVariables.sefer_number == 1){                 //KADIKOY-BESIKTAS

                        if (annoncementArea(SeferVariables.Besiktas_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                     announcementPlayer.playAnnouncement(R.raw.besiktas_iskelesi)
                                    SeferVariables.currentLocation = 2
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Kadikoy_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.kadikoy_iskelesi)
                                    SeferVariables.currentLocation = 4
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else{
                        SeferVariables.announcementCounter=0
                        }
                    }

                    if(SeferVariables.sefer_number == 2){                    //USKUDAR-BESIKTAS

                        if (annoncementArea(SeferVariables.Besiktas_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.besiktas_iskelesi)
                                    SeferVariables.currentLocation = 2
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Uskudar_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.uskudar_iskelesi)
                                    SeferVariables.currentLocation = 1
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else{
                            SeferVariables.announcementCounter=0
                        }
                    }

                    if(SeferVariables.sefer_number == 3){                   // KADIKOY-KABATAS

                        if (annoncementArea(SeferVariables.Kadikoy_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.kadikoy_iskelesi)
                                    SeferVariables.currentLocation = 4
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Kabatas_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.kabatas_iskelesi)
                                    SeferVariables.currentLocation = 3
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else{
                            SeferVariables.announcementCounter=0
                        }

                    }

                    if(SeferVariables.sefer_number == 4){                     //USKUDAR-KABATAS

                        if (annoncementArea(SeferVariables.Uskudar_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.uskudar_iskelesi)
                                    SeferVariables.currentLocation = 1
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Kabatas_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.kabatas_iskelesi)
                                    SeferVariables.currentLocation = 3
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else{
                            SeferVariables.announcementCounter=0
                        }

                    }


                    if(SeferVariables.sefer_number == 5){                     //3NO HISAR SABAH

                        if (annoncementArea(SeferVariables.Eminonu_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.eminonu_iskelesi)
                                    SeferVariables.currentLocation = 5
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Besiktas_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.besiktas_iskelesi)
                                    SeferVariables.currentLocation = 2
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Cengelkoy_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.cengelkoy_iskelesi)
                                    SeferVariables.currentLocation = 12
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Kandilli_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.kandilli_iskelesi)
                                    SeferVariables.currentLocation = 13
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Ahisari_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.anadoluhisari_iskelesi)
                                    SeferVariables.currentLocation = 14
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else{
                            SeferVariables.announcementCounter=0
                        }

                    }


                    if(SeferVariables.sefer_number == 6){                     //3NO HISAR AKŞAM

                        if (annoncementArea(SeferVariables.Eminonu_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.eminonu_iskelesi)
                                    SeferVariables.currentLocation = 5
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Besiktas_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.besiktas_iskelesi)
                                    SeferVariables.currentLocation = 2
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Kuzguncuk_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.kuzguncuk_iskelesi)
                                    SeferVariables.currentLocation = 10
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Beylerbeyi_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.beylerbeyi_iskelesi)
                                    SeferVariables.currentLocation = 11
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Cengelkoy_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.cengelkoy_iskelesi)
                                    SeferVariables.currentLocation = 12
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Ahisari_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.anadoluhisari_iskelesi)
                                    SeferVariables.currentLocation = 14
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else{
                            SeferVariables.announcementCounter=0
                        }

                    }


                    if(SeferVariables.sefer_number == 7){                     //6NO ORTABOGAZ

                        if (annoncementArea(SeferVariables.Cengelkoy_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.cengelkoy_iskelesi)
                                    SeferVariables.currentLocation = 12
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Arnavutkoy_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.arnavutkoy_iskelesi)
                                    SeferVariables.currentLocation = 15
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Bebek_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.bebek_iskelesi)
                                    SeferVariables.currentLocation = 16
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Kandilli_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.kandilli_iskelesi)
                                    SeferVariables.currentLocation = 13
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Ahisari_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.anadoluhisari_iskelesi)
                                    SeferVariables.currentLocation = 14
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Kanlica_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.kanlica_iskelesi)
                                    SeferVariables.currentLocation = 19
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Emirgan_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.emirgan_iskelesi)
                                    SeferVariables.currentLocation = 17
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Istinye_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.istinye_iskelesi)
                                    SeferVariables.currentLocation = 18
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else{
                            SeferVariables.announcementCounter=0
                        }

                    }


                    if(SeferVariables.sefer_number == 8){                     //6NO ORTABOGAZ PAZAR

                        if (annoncementArea(SeferVariables.Bebek_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.bebek_iskelesi)
                                    SeferVariables.currentLocation = 16
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Ahisari_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.anadoluhisari_iskelesi)
                                    SeferVariables.currentLocation = 14
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Kanlica_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.kanlica_iskelesi)
                                    SeferVariables.currentLocation = 19
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Emirgan_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.emirgan_iskelesi)
                                    SeferVariables.currentLocation = 17
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else{
                            SeferVariables.announcementCounter=0
                        }

                    }



                    if(SeferVariables.sefer_number == 9){                     //7NO KÜCÜKSU NORMAL

                        if (annoncementArea(SeferVariables.Besiktas_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.besiktas_iskelesi)
                                    SeferVariables.currentLocation = 2
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Kabatas_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.kabatas_iskelesi)
                                    SeferVariables.currentLocation = 3
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Kucuksu_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.kucuksu_iskelesi)
                                    SeferVariables.currentLocation = 20
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Kanlica_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.kanlica_iskelesi)
                                    SeferVariables.currentLocation = 19
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else{
                            SeferVariables.announcementCounter=0
                        }

                    }



                    if(SeferVariables.sefer_number == 10){                     //7NO KÜCÜKSU AKŞAM

                        if (annoncementArea(SeferVariables.Besiktas_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.besiktas_iskelesi)
                                    SeferVariables.currentLocation = 2
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Kabatas_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.kabatas_iskelesi)
                                    SeferVariables.currentLocation = 3
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Kucuksu_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.kucuksu_iskelesi)
                                    SeferVariables.currentLocation = 20
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Kanlica_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.kanlica_iskelesi)
                                    SeferVariables.currentLocation = 19
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Eminonu_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.eminonu_iskelesi)
                                    SeferVariables.currentLocation = 5
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Beylerbeyi_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.beylerbeyi_iskelesi)
                                    SeferVariables.currentLocation = 11
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Cengelkoy_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.cengelkoy_iskelesi)
                                    SeferVariables.currentLocation = 12
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else{
                            SeferVariables.announcementCounter=0
                        }

                    }


                    if(SeferVariables.sefer_number == 11){                     //8NO SARIYER HAFTACİCİ SABAH

                        if (annoncementArea(SeferVariables.Eminonu_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.eminonu_iskelesi)
                                    SeferVariables.currentLocation = 5
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Kabatas_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.kabatas_iskelesi)
                                    SeferVariables.currentLocation = 3
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Besiktas_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.besiktas_iskelesi)
                                    SeferVariables.currentLocation = 2
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Kuzguncuk_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.kuzguncuk_iskelesi)
                                    SeferVariables.currentLocation = 10
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Beylerbeyi_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.beylerbeyi_iskelesi)
                                    SeferVariables.currentLocation = 11
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Emirgan_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.emirgan_iskelesi)
                                    SeferVariables.currentLocation = 17
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Istinye_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.istinye_iskelesi)
                                    SeferVariables.currentLocation = 18
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Sariyer_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.sariyer_iskelesi)
                                    SeferVariables.currentLocation = 22
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else{
                            SeferVariables.announcementCounter=0
                        }

                    }

                    if(SeferVariables.sefer_number == 12){                     //8NO SARIYER HAFTACİCİ AKSAM

                        if (annoncementArea(SeferVariables.Eminonu_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.eminonu_iskelesi)
                                    SeferVariables.currentLocation = 5
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Kabatas_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.kabatas_iskelesi)
                                    SeferVariables.currentLocation = 3
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Besiktas_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.besiktas_iskelesi)
                                    SeferVariables.currentLocation = 2
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Ortakoy_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.ortakoy_iskelesi)
                                    SeferVariables.currentLocation = 21
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Emirgan_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.emirgan_iskelesi)
                                    SeferVariables.currentLocation = 17
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Istinye_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.istinye_iskelesi)
                                    SeferVariables.currentLocation = 18
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Sariyer_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.sariyer_iskelesi)
                                    SeferVariables.currentLocation = 22
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else{
                            SeferVariables.announcementCounter=0
                        }

                    }


                    if(SeferVariables.sefer_number == 13){                     //8NO SARIYER CUMARTESİ SABAH

                        if (annoncementArea(SeferVariables.Eminonu_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.eminonu_iskelesi)
                                    SeferVariables.currentLocation = 5
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Kabatas_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.kabatas_iskelesi)
                                    SeferVariables.currentLocation = 3
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Besiktas_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.besiktas_iskelesi)
                                    SeferVariables.currentLocation = 2
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Beylerbeyi_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.beylerbeyi_iskelesi)
                                    SeferVariables.currentLocation = 11
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }

                        else if (annoncementArea(SeferVariables.Emirgan_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.emirgan_iskelesi)
                                    SeferVariables.currentLocation = 17
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Istinye_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.istinye_iskelesi)
                                    SeferVariables.currentLocation = 18
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Sariyer_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.sariyer_iskelesi)
                                    SeferVariables.currentLocation = 22
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else{
                            SeferVariables.announcementCounter=0
                        }

                    }


                    if(SeferVariables.sefer_number == 14){                     //8NO SARIYER CUMARTESİ AKSAM

                        if (annoncementArea(SeferVariables.Eminonu_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.eminonu_iskelesi)
                                    SeferVariables.currentLocation = 5
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Kabatas_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.kabatas_iskelesi)
                                    SeferVariables.currentLocation = 3
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Besiktas_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.besiktas_iskelesi)
                                    SeferVariables.currentLocation = 2
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Ortakoy_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.ortakoy_iskelesi)
                                    SeferVariables.currentLocation = 21
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Cengelkoy_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.cengelkoy_iskelesi)
                                    SeferVariables.currentLocation = 12
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Emirgan_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.emirgan_iskelesi)
                                    SeferVariables.currentLocation = 17
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Istinye_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.istinye_iskelesi)
                                    SeferVariables.currentLocation = 18
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Sariyer_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.sariyer_iskelesi)
                                    SeferVariables.currentLocation = 22
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else{
                            SeferVariables.announcementCounter=0
                        }

                    }


                    if(SeferVariables.sefer_number == 15){                     //8NO SARIYER PAZAR

                        if (annoncementArea(SeferVariables.Eminonu_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.eminonu_iskelesi)
                                    SeferVariables.currentLocation = 5
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Besiktas_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.besiktas_iskelesi)
                                    SeferVariables.currentLocation = 2
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Kanlica_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.kanlica_iskelesi)
                                    SeferVariables.currentLocation = 19
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Sariyer_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.sariyer_iskelesi)
                                    SeferVariables.currentLocation = 22
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else{
                            SeferVariables.announcementCounter=0
                        }

                    }



                    if(SeferVariables.sefer_number == 16){                     //9NO BEYKOZ SABAH

                        if (annoncementArea(SeferVariables.Kabatas_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.kabatas_iskelesi)
                                    SeferVariables.currentLocation = 3
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Uskudar_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.uskudar_iskelesi)
                                    SeferVariables.currentLocation = 1
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Kucuksu_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.kucuksu_iskelesi)
                                    SeferVariables.currentLocation = 20
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Ahisari_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.anadoluhisari_iskelesi)
                                    SeferVariables.currentLocation = 14
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Cengelkoy_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.cengelkoy_iskelesi)
                                    SeferVariables.currentLocation = 12
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Pasabahce_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.pasabahce_iskelesi)
                                    SeferVariables.currentLocation = 23
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Beykoz_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.beykoz_iskelesi)
                                    SeferVariables.currentLocation = 24
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else{
                            SeferVariables.announcementCounter=0
                        }

                    }


                    if(SeferVariables.sefer_number == 17){                     //9NO BEYKOZ AKSAM

                        if (annoncementArea(SeferVariables.Kabatas_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.kabatas_iskelesi)
                                    SeferVariables.currentLocation = 3
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Kuzguncuk_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.kuzguncuk_iskelesi)
                                    SeferVariables.currentLocation = 10
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Uskudar_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.uskudar_iskelesi)
                                    SeferVariables.currentLocation = 1
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Kucuksu_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.kucuksu_iskelesi)
                                    SeferVariables.currentLocation = 20
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Ahisari_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.anadoluhisari_iskelesi)
                                    SeferVariables.currentLocation = 14
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Cengelkoy_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.cengelkoy_iskelesi)
                                    SeferVariables.currentLocation = 12
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Pasabahce_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.pasabahce_iskelesi)
                                    SeferVariables.currentLocation = 23
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Beykoz_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.beykoz_iskelesi)
                                    SeferVariables.currentLocation = 24
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else{
                            SeferVariables.announcementCounter=0
                        }

                    }


                    if(SeferVariables.sefer_number == 18){                     //SH ADALAR

                        if (annoncementArea(SeferVariables.Besiktas_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.besiktas_iskelesi)
                                    SeferVariables.currentLocation = 2
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Kadikoy_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.kadikoy_iskelesi)
                                    SeferVariables.currentLocation = 4
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Kinaliada_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.kinaliada_iskelesi)
                                    SeferVariables.currentLocation = 6
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Burgazada_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.burgazada_iskelesi)
                                    SeferVariables.currentLocation = 7
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Heybeliada_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.heybeliada_iskelesi)
                                    SeferVariables.currentLocation = 8
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Buyukada_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.buyukada_iskelesi)
                                    SeferVariables.currentLocation = 9
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else{
                            SeferVariables.announcementCounter=0
                        }

                    }


                    if(SeferVariables.sefer_number == 19){                     //20NO BEYKOZ

                        if (annoncementArea(SeferVariables.Uskudar_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.uskudar_iskelesi)
                                    SeferVariables.currentLocation = 1
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Beylerbeyi_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.beylerbeyi_iskelesi)
                                    SeferVariables.currentLocation = 11
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Cengelkoy_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.cengelkoy_iskelesi)
                                    SeferVariables.currentLocation = 12
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Ahisari_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.anadoluhisari_iskelesi)
                                    SeferVariables.currentLocation = 14
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Kanlica_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.kanlica_iskelesi)
                                    SeferVariables.currentLocation = 19
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Cubuklu_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.cubuklu_iskelesi)
                                    SeferVariables.currentLocation = 25
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Pasabahce_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.pasabahce_iskelesi)
                                    SeferVariables.currentLocation = 23
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Beykoz_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.beykoz_iskelesi)
                                    SeferVariables.currentLocation = 24
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else{
                            SeferVariables.announcementCounter=0
                        }

                    }

                    if(SeferVariables.sefer_number == 20){                     //21NO ANADOLU KAVAGI

                        if (annoncementArea(SeferVariables.Uskudar_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.uskudar_iskelesi)
                                    SeferVariables.currentLocation = 1
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Beylerbeyi_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.beylerbeyi_iskelesi)
                                    SeferVariables.currentLocation = 11
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Cengelkoy_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.cengelkoy_iskelesi)
                                    SeferVariables.currentLocation = 12
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Ahisari_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.anadoluhisari_iskelesi)
                                    SeferVariables.currentLocation = 14
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Kanlica_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.kanlica_iskelesi)
                                    SeferVariables.currentLocation = 19
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Cubuklu_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.cubuklu_iskelesi)
                                    SeferVariables.currentLocation = 25
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Pasabahce_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.pasabahce_iskelesi)
                                    SeferVariables.currentLocation = 23
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Beykoz_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.beykoz_iskelesi)
                                    SeferVariables.currentLocation = 24
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Akavagi_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.anadolukavagi_iskelesi)
                                    SeferVariables.currentLocation = 26
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else{
                            SeferVariables.announcementCounter=0
                        }

                    }



                    if(SeferVariables.sefer_number == 21){                     //23NO BEYKOZ

                        if (annoncementArea(SeferVariables.Uskudar_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.uskudar_iskelesi)
                                    SeferVariables.currentLocation = 1
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Beylerbeyi_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.beylerbeyi_iskelesi)
                                    SeferVariables.currentLocation = 11
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Cengelkoy_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.cengelkoy_iskelesi)
                                    SeferVariables.currentLocation = 12
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Ahisari_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.anadoluhisari_iskelesi)
                                    SeferVariables.currentLocation = 14
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Kanlica_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.kanlica_iskelesi)
                                    SeferVariables.currentLocation = 19
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Cubuklu_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.cubuklu_iskelesi)
                                    SeferVariables.currentLocation = 25
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Pasabahce_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.pasabahce_iskelesi)
                                    SeferVariables.currentLocation = 23
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Beykoz_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.beykoz_iskelesi)
                                    SeferVariables.currentLocation = 24
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Akavagi_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.anadolukavagi_iskelesi)
                                    SeferVariables.currentLocation = 26
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else{
                            SeferVariables.announcementCounter=0
                        }

                    }

                    if(SeferVariables.sefer_number == 22){                     //24NO BEYKOZ

                        if (annoncementArea(SeferVariables.Eminonu_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.eminonu_iskelesi)
                                    SeferVariables.currentLocation = 5
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Besiktas_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.besiktas_iskelesi)
                                    SeferVariables.currentLocation = 2
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Arnavutkoy_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.arnavutkoy_iskelesi)
                                    SeferVariables.currentLocation = 15
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Emirgan_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.emirgan_iskelesi)
                                    SeferVariables.currentLocation = 17
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Istinye_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.istinye_iskelesi)
                                    SeferVariables.currentLocation = 18
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Buyukdere_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.buyukdere_iskelesi)
                                    SeferVariables.currentLocation = 28
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Sariyer_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.sariyer_iskelesi)
                                    SeferVariables.currentLocation = 22
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Akavagi_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.anadolukavagi_iskelesi)
                                    SeferVariables.currentLocation = 26
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else{
                            SeferVariables.announcementCounter=0
                        }

                    }


                    if(SeferVariables.sefer_number == 23){                     //emn-bes-ort

                    if (annoncementArea(SeferVariables.Eminonu_iskele,SeferVariables.location_data)) {
                        if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                            if(!SeferVariables.playerState){
                                announcementPlayer.playAnnouncement(R.raw.eminonu_iskelesi)
                                SeferVariables.currentLocation = 5
                                SeferVariables.announcementCounter++
                            }
                        }
                    }
                    else if (annoncementArea(SeferVariables.Besiktas_iskele,SeferVariables.location_data)) {
                        if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                            if(!SeferVariables.playerState){
                                announcementPlayer.playAnnouncement(R.raw.besiktas_iskelesi)
                                SeferVariables.currentLocation = 2
                                SeferVariables.announcementCounter++
                            }
                        }
                    }
                    else if (annoncementArea(SeferVariables.Ortakoy_iskele,SeferVariables.location_data)) {
                        if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                            if(!SeferVariables.playerState){
                                announcementPlayer.playAnnouncement(R.raw.ortakoy_iskelesi)
                                SeferVariables.currentLocation = 21
                                SeferVariables.announcementCounter++
                            }
                        }
                    }
                    else{
                        SeferVariables.announcementCounter=0
                    }

                }


                    if(SeferVariables.sefer_number == 24){                     //kabatas-cengel

                        if (annoncementArea(SeferVariables.Cengelkoy_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.cengelkoy_iskelesi)
                                    SeferVariables.currentLocation = 12
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Kabatas_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.kabatas_iskelesi)
                                    SeferVariables.currentLocation = 3
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else{
                            SeferVariables.announcementCounter=0
                        }

                    }

                    if(SeferVariables.sefer_number == 25){                     //dentur adalar

                        if (annoncementArea(SeferVariables.Besiktas_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.besiktas_iskelesi)
                                    SeferVariables.currentLocation = 2
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        if (annoncementArea(SeferVariables.Eminonu_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.eminonu_iskelesi)
                                    SeferVariables.currentLocation = 5
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Kabatas_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.kabatas_iskelesi)
                                    SeferVariables.currentLocation = 3
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Kinaliada_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.kinaliada_iskelesi)
                                    SeferVariables.currentLocation = 6
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Burgazada_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.burgazada_iskelesi)
                                    SeferVariables.currentLocation = 7
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Heybeliada_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.heybeliada_iskelesi)
                                    SeferVariables.currentLocation = 8
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else if (annoncementArea(SeferVariables.Buyukada_iskele,SeferVariables.location_data)) {
                            if(SeferVariables.announcementCounter<SeferVariables.announcementRepeatNumber){
                                if(!SeferVariables.playerState){
                                    announcementPlayer.playAnnouncement(R.raw.buyukada_iskelesi)
                                    SeferVariables.currentLocation = 9
                                    SeferVariables.announcementCounter++
                                }
                            }
                        }
                        else{
                            SeferVariables.announcementCounter=0
                        }

                    }

                }







                CoroutineScope(Dispatchers.IO).launch { // OTO ANONS

                if(SeferVariables.otoAnonsState==1) {
                    when (SeferVariables.otoAnonsTimer) {
                        1 -> {
                            if (!SeferVariables.playerStateOto) {
                                announcementPlayer.playAutoAnnouncement(R.raw.sigara_anonsu, 2)
                            }
                        }
                        2 -> {
                            if (!SeferVariables.playerStateOto) {
                                announcementPlayer.playAutoAnnouncement(R.raw.sigara_anonsu, 5)
                            }
                        }
                        3 -> {
                            if (!SeferVariables.playerStateOto) {
                                announcementPlayer.playAutoAnnouncement(R.raw.sigara_anonsu, 10)
                            }
                        }
                        4 -> {
                            if (!SeferVariables.playerStateOto) {
                                announcementPlayer.playAutoAnnouncement(R.raw.sigara_anonsu, 15)
                            }
                        }
                        5 -> {
                            if (!SeferVariables.playerStateOto) {
                                announcementPlayer.playAutoAnnouncement(R.raw.sigara_anonsu, 20)
                            }
                        }
                        6 -> {
                            if (!SeferVariables.playerStateOto) {
                                announcementPlayer.playAutoAnnouncement(R.raw.sigara_anonsu, 30)
                            }
                        }
                        7 -> {
                            if (!SeferVariables.playerStateOto) {
                                announcementPlayer.playAutoAnnouncement(R.raw.sigara_anonsu, 45)
                            }
                        }
                        8 -> {
                            if (!SeferVariables.playerStateOto) {
                                announcementPlayer.playAutoAnnouncement(R.raw.sigara_anonsu, 60)
                            }
                        }
                    }
                }
                }

                //println(SeferVariables.playerVolume)
                oneTimeAnnouncement(SeferVariables.announcement_val)
                handler.postDelayed(this, 1)
            }
        })
    }

    fun annoncementArea(spesific_loc: DoubleArray,current_loc:DoubleArray): Boolean{
        return spesific_loc[0]<current_loc[0]&&
                spesific_loc[2]>current_loc[0]&&
                spesific_loc[1]<current_loc[1]&&
                spesific_loc[3]>current_loc[1]
    }

    fun oneTimeAnnouncement(ann_number :Int){

        when(ann_number){
            0 ->{
                //HİÇLİK
            }
            1 ->{
                announcementPlayer.play(R.raw.besiktas_iskelesi)
                SeferVariables.announcement_val = 0
            }
            2 ->{
                announcementPlayer.play(R.raw.uskudar_iskelesi)
                SeferVariables.announcement_val = 0
            }
            3 ->{
                announcementPlayer.play(R.raw.kabatas_iskelesi)
                SeferVariables.announcement_val = 0
            }
            4 ->{
                announcementPlayer.play(R.raw.kadikoy_iskelesi)
                SeferVariables.announcement_val = 0
            }
            5 ->{
                announcementPlayer.play(R.raw.eminonu_iskelesi)
                SeferVariables.announcement_val = 0
            }
            6 ->{
                announcementPlayer.play(R.raw.kinaliada_iskelesi)
                SeferVariables.announcement_val = 0
            }
            7 ->{
                announcementPlayer.play(R.raw.burgazada_iskelesi)
                SeferVariables.announcement_val = 0
            }
            8 ->{
                announcementPlayer.play(R.raw.heybeliada_iskelesi)
                SeferVariables.announcement_val = 0
            }
            9 ->{
                announcementPlayer.play(R.raw.buyukada_iskelesi)//
                SeferVariables.announcement_val = 0
            }
            10 ->{
                announcementPlayer.play(R.raw.anadoluhisari_iskelesi)
                SeferVariables.announcement_val = 0
            }
            11 ->{
                announcementPlayer.play(R.raw.kandilli_iskelesi)
                SeferVariables.announcement_val = 0
            }
            12 ->{
                announcementPlayer.play(R.raw.kuzguncuk_iskelesi)
                SeferVariables.announcement_val = 0
            }
            13 ->{
                announcementPlayer.play(R.raw.beylerbeyi_iskelesi)
                SeferVariables.announcement_val = 0
            }
            14 ->{
                announcementPlayer.play(R.raw.cengelkoy_iskelesi)
                SeferVariables.announcement_val = 0
            }
            15 ->{
                announcementPlayer.play(R.raw.kucuksu_iskelesi)//
                SeferVariables.announcement_val = 0
            }
            16 ->{
                announcementPlayer.play(R.raw.ortakoy_iskelesi)
                SeferVariables.announcement_val = 0
            }
            17 ->{
                announcementPlayer.play(R.raw.emirgan_iskelesi)//
                SeferVariables.announcement_val = 0
            }
            18 ->{
                announcementPlayer.play(R.raw.istinye_iskelesi)
                SeferVariables.announcement_val = 0
            }
            19 ->{
                announcementPlayer.play(R.raw.sariyer_iskelesi)
                SeferVariables.announcement_val = 0
            }
            20 ->{
                announcementPlayer.play(R.raw.kanlica_iskelesi)//
                SeferVariables.announcement_val = 0
            }
            21 ->{
                announcementPlayer.play(R.raw.pasabahce_iskelesi)
                SeferVariables.announcement_val = 0
            }
            22->{
                announcementPlayer.play(R.raw.beykoz_iskelesi)
                SeferVariables.announcement_val = 0
            }
            23 ->{
                announcementPlayer.play(R.raw.cubuklu_iskelesi)
                SeferVariables.announcement_val = 0
            }
            24 ->{
                announcementPlayer.play(R.raw.anadolukavagi_iskelesi)
                SeferVariables.announcement_val = 0
            }
            25 ->{
                announcementPlayer.play(R.raw.arnavutkoy_iskelesi)
                SeferVariables.announcement_val = 0
            }
            26 ->{
                announcementPlayer.play(R.raw.buyukdere_iskelesi)
                SeferVariables.announcement_val = 0
            }
            27 ->{
                announcementPlayer.play(R.raw.rumelikavagi_iskelesi)
                SeferVariables.announcement_val = 0
            }
            28 ->{
                announcementPlayer.play(R.raw.bebek_iskelesi)
                SeferVariables.announcement_val = 0
            }
            29 ->{
                announcementPlayer.play(R.raw.sigara_anonsu)
                SeferVariables.announcement_val = 0
            }

        }


    }



}