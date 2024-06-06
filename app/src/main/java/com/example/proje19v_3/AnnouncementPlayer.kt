package com.example.proje19v_3

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Build
import java.util.Timer
import java.util.TimerTask
import kotlin.concurrent.thread

class AnnouncementPlayer(private val context: Context) {

    private var mediaPlayer: MediaPlayer? = null
    private lateinit var timer: Timer
    var annDelay : Long  = SeferVariables.announcementDelayTime*1000.toLong()



    fun play(audioId: Int) {
        SeferVariables.playerState = true
        println("anons yapıyoru")
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer.create(context, audioId)
        mediaPlayer?.start()
        mediaPlayer?.setVolume(SeferVariables.playerVolume,SeferVariables.playerVolume)
        mediaPlayer?.setOnCompletionListener {
            SeferVariables.announcement_val = 0
            SeferVariables.playerState = false
            println("anons bitti")
        }
    }

    fun playAnnouncement(audioResId: Int) {
        SeferVariables.playerState = true
        mediaPlayer = MediaPlayer.create(context, audioResId)
        mediaPlayer?.start()
        mediaPlayer?.setVolume(SeferVariables.playerVolume,SeferVariables.playerVolume)
        mediaPlayer?.setOnCompletionListener {
            SeferVariables.playerState = false
            Thread.sleep(annDelay)

        }
    }

     fun playAutoAnnouncement(audioResId: Int,timeInterval:Int) {
         val timeInMin = timeInterval*60000
         timer = Timer()
         SeferVariables.playerStateOto = true
         timer.schedule(object : TimerTask() {
             override fun run() {
                 mediaPlayer = MediaPlayer.create(context, audioResId)
                 mediaPlayer?.start()
                 mediaPlayer?.setVolume(SeferVariables.playerVolume,SeferVariables.playerVolume)
                 mediaPlayer?.setOnCompletionListener {
                     SeferVariables.playerStateOto=false
                 }
             }
         }, timeInMin.toLong())
    }

    fun release() {
        mediaPlayer?.release()
        mediaPlayer = null
    }
}