package com.example.proje19v_3

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.proje19v_3.databinding.FragmentAnnouncementBinding
import com.example.proje19v_3.databinding.FragmentOtoAnonsBinding

class FragmentOtoAnons : Fragment() {
    private lateinit var binding: FragmentOtoAnonsBinding
    private var otoCounter : Int = 1
    private var otoStateCounter : Int = 0
    private var otoState : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOtoAnonsBinding.inflate(inflater,container,false)
        SeferVariables.otoAnonsState = 0

        binding.otoPlus.setOnClickListener{
            otoCounter++
            refreshText()
        }
        binding.otoMinus.setOnClickListener{
            otoCounter--
            refreshText()
        }

        binding.otoStart.setOnClickListener{
            otoState  = otoCounter % 2
            SeferVariables.otoAnonsState = 1
            if (otoCounter>8){
                otoCounter=8
            }
            if (otoCounter<1){
                otoCounter=1
            }

            when(otoCounter){
                1-> SeferVariables.otoAnonsTimer = 1
                2-> SeferVariables.otoAnonsTimer = 2
                3-> SeferVariables.otoAnonsTimer = 3
                4-> SeferVariables.otoAnonsTimer = 4
                5-> SeferVariables.otoAnonsTimer = 5
                6-> SeferVariables.otoAnonsTimer = 6
                7-> SeferVariables.otoAnonsTimer = 7
                8-> SeferVariables.otoAnonsTimer = 8
            }
            SeferVariables.otoAnonsTimer = otoCounter
            SeferVariables.btn_home_state = true
            println("otoAnonsTimer=${SeferVariables.otoAnonsTimer}  otoAnonsState = ${SeferVariables.otoAnonsState}")

        }


        return binding.root
    }

    @SuppressLint("SetTextI18n")
    fun refreshText(){
        if(otoCounter>8){
            otoCounter=8
        }
        if(otoCounter<1){
            otoCounter=1
        }
        when(otoCounter){
            1-> binding.textAnonsTime.text ="2 dak"
            2-> binding.textAnonsTime.text ="5 dak"
            3-> binding.textAnonsTime.text ="10 dak"
            4-> binding.textAnonsTime.text ="15 dak"
            5-> binding.textAnonsTime.text ="20 dak"
            6-> binding.textAnonsTime.text ="30 dak"
            7-> binding.textAnonsTime.text ="45 dak"
            8-> binding.textAnonsTime.text ="60 dak"
        }
    }
}