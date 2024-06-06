package com.example.proje19v_3

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import com.example.proje19v_3.databinding.FragmentGemiAnonsBinding
import com.example.proje19v_3.databinding.FragmentSettingsBinding

class FragmentSettings : Fragment(R.layout.fragment_settings) {

    private var timeVal: Int = SeferVariables.announcementDelayTime
    private var countVal : Int = SeferVariables.announcementRepeatNumber

    private var counterVizMap: Int = 0
    private var counterVizLoc: Int = 0
    private var counterVizVol: Int = 0
    private var stateVizMap: Int = 0
    private var stateVizLoc: Int = 0
    private var stateVizVol: Int = 0

    private lateinit var binding: FragmentSettingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater,container,false)

        refreshPage()

        if(SeferVariables.visiblity_map){
            stateVizMap=0
            binding.btnVizMap.text = "KALDIR"
        }
        if(!SeferVariables.visiblity_map){
            stateVizMap=1
        }

        if(SeferVariables.visiblity_loc){
            stateVizLoc=0
            binding.btnVizLoc.text = "KALDIR"
        }
        if(!SeferVariables.visiblity_loc){
            stateVizLoc=1
        }

        if(SeferVariables.visiblity_vol){
            stateVizVol=0
            binding.btnVizVol.text = "KALDIR"
        }
        if(!SeferVariables.visiblity_vol){
            stateVizVol=1
        }


        binding.btnPlus1.setOnClickListener {

            timeVal= timeVal + 5
            SeferVariables.announcementRepeatNumber= countVal
            SeferVariables.announcementDelayTime= timeVal
            refreshPage()
        }
        binding.btnMinus1.setOnClickListener {

            timeVal= timeVal - 5
            SeferVariables.announcementRepeatNumber= countVal
            SeferVariables.announcementDelayTime= timeVal
            refreshPage()
        }

        binding.btnPlus2.setOnClickListener {

            countVal++
            SeferVariables.announcementRepeatNumber= countVal
            SeferVariables.announcementDelayTime= timeVal
            refreshPage()
        }
        binding.btnMinus2.setOnClickListener {

            countVal--
            SeferVariables.announcementRepeatNumber= countVal
            SeferVariables.announcementDelayTime= timeVal
            refreshPage()

        }

        binding.btnVizMap.setOnClickListener {
            counterVizMap++
            stateVizMap = counterVizMap % 2
            println("counter: $counterVizMap  state: $stateVizMap")
            refreshPage()
            if (stateVizMap == 1){
                SeferVariables.visiblity_map = true
                binding.btnVizMap.text = "KALDIR"
            }
            if (stateVizMap == 0){
                SeferVariables.visiblity_map = false
                binding.btnVizMap.text = "EKLE"
            }
        }

        binding.btnVizLoc.setOnClickListener {
            counterVizLoc++
            stateVizLoc = counterVizLoc % 2
            refreshPage()
            if (stateVizLoc == 1){
                SeferVariables.visiblity_loc = true
                binding.btnVizLoc.text = "KALDIR"
            }
            if (stateVizLoc == 0){
                SeferVariables.visiblity_loc = false
                binding.btnVizLoc.text = "EKLE"
            }
        }

        binding.btnVizVol.setOnClickListener {
            counterVizVol++
            stateVizVol = counterVizVol % 2
            refreshPage()
            if (stateVizVol == 1){
                SeferVariables.visiblity_vol =true
                binding.btnVizVol.text = "KALDIR"
            }
            if (stateVizVol == 0){
                SeferVariables.visiblity_vol= false
                binding.btnVizVol.text= "EKLE"
            }
        }



        binding.volBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                var volume : Float = progress/100.toFloat()
                SeferVariables.playerVolume = volume
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

            }
        )

        return binding.root
    }

    fun refreshPage(){
        binding.textAnonsCount.text = countVal.toString()
        binding.textAnonsTime.text = "${timeVal}sn"
        binding.volBar.setProgress((SeferVariables.playerVolume*100).toInt())
    }

}