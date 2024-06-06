package com.example.proje19v_3

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.example.proje19v_3.databinding.FragmentHomeBinding
import com.example.proje19v_3.databinding.FragmentSeferSecimBinding

class FragmentSeferSecim : Fragment(R.layout.fragment_sefer_secim) {
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var binding: FragmentSeferSecimBinding
    private var seferDetail : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loop()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSeferSecimBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onDestroy() {

        handler.removeCallbacksAndMessages(null)
        super.onDestroy()
    }

    private fun loop() {
        handler.post(object : Runnable {
            override fun run() {

                println(seferDetail)

                binding.btn1.setOnClickListener{
                    seferDetail = 1
                }
                if (seferDetail==1){
                    binding.seferdetail1.isVisible=true
                    binding.btn1secim.setOnClickListener{
                        SeferVariables.btn_home_state = true
                        SeferVariables.sefer_number = 1
                    }
                }
                else{
                    binding.seferdetail1.isVisible=false
                }


                binding.btn2.setOnClickListener{
                    seferDetail = 2
                }
                if (seferDetail==2){
                    binding.seferdetail2.isVisible=true
                    binding.btn2secim.setOnClickListener{
                        SeferVariables.btn_home_state = true
                        SeferVariables.sefer_number = 2
                    }
                }
                else{
                    binding.seferdetail2.isVisible=false
                }


                binding.btn3.setOnClickListener{
                    seferDetail = 3
                }
                if (seferDetail==3){
                    binding.seferdetail3.isVisible=true
                    binding.btn3secim.setOnClickListener{
                        SeferVariables.btn_home_state = true
                        SeferVariables.sefer_number = 3
                    }
                }
                else{
                    binding.seferdetail3.isVisible=false
                }


                binding.btn4.setOnClickListener{
                    seferDetail = 4
                }
                if (seferDetail==4){
                    binding.seferdetail4.isVisible=true
                    binding.btn4secim.setOnClickListener{
                        SeferVariables.btn_home_state = true
                        SeferVariables.sefer_number = 4
                    }
                }
                else{
                    binding.seferdetail4.isVisible=false
                }


                binding.btn5.setOnClickListener{
                    seferDetail = 5
                }
                if (seferDetail==5){
                    binding.seferdetail5.isVisible=true
                    binding.btn5secim.setOnClickListener{
                        SeferVariables.btn_home_state = true
                        SeferVariables.sefer_number = 5
                    }
                    binding.btn6secim.setOnClickListener{
                        SeferVariables.btn_home_state = true
                        SeferVariables.sefer_number = 6
                    }
                }
                else{
                    binding.seferdetail5.isVisible=false
                }


                binding.btn7.setOnClickListener{
                    seferDetail = 7
                }
                if (seferDetail==7){
                    binding.seferdetail7.isVisible=true
                    binding.btn7secim.setOnClickListener{
                        SeferVariables.btn_home_state = true
                        SeferVariables.sefer_number = 7
                    }
                    binding.btn8secim.setOnClickListener{
                        SeferVariables.btn_home_state = true
                        SeferVariables.sefer_number = 8
                    }
                }
                else{
                    binding.seferdetail7.isVisible=false
                }



                binding.btn9.setOnClickListener{
                    seferDetail = 9
                }
                if (seferDetail==9){
                    binding.seferdetail9.isVisible=true
                    binding.btn9secim.setOnClickListener{
                        SeferVariables.btn_home_state = true
                        SeferVariables.sefer_number = 9
                    }
                    binding.btn10secim.setOnClickListener{
                        SeferVariables.btn_home_state = true
                        SeferVariables.sefer_number = 10
                    }
                }
                else{
                    binding.seferdetail9.isVisible=false
                }


                binding.btn11.setOnClickListener{
                    seferDetail = 11
                }
                if (seferDetail==11){
                    binding.seferdetail11.isVisible=true
                    binding.btn11secim.setOnClickListener{
                        SeferVariables.btn_home_state = true
                        SeferVariables.sefer_number = 11
                    }
                    binding.btn12secim.setOnClickListener{
                        SeferVariables.btn_home_state = true
                        SeferVariables.sefer_number = 12
                    }
                    binding.btn13secim.setOnClickListener{
                        SeferVariables.btn_home_state = true
                        SeferVariables.sefer_number = 13
                    }
                    binding.btn14secim.setOnClickListener{
                        SeferVariables.btn_home_state = true
                        SeferVariables.sefer_number = 14
                    }
                    binding.btn15secim.setOnClickListener{
                        SeferVariables.btn_home_state = true
                        SeferVariables.sefer_number = 15
                    }
                }
                else{
                    binding.seferdetail11.isVisible=false
                }


                binding.btn16.setOnClickListener{
                    seferDetail = 16
                }
                if (seferDetail==16){
                    binding.seferdetail16.isVisible=true
                    binding.btn16secim.setOnClickListener{
                        SeferVariables.btn_home_state = true
                        SeferVariables.sefer_number = 16
                    }
                    binding.btn17secim.setOnClickListener{
                        SeferVariables.btn_home_state = true
                        SeferVariables.sefer_number = 17
                    }

                }
                else{
                    binding.seferdetail16.isVisible=false
                }


                binding.btn18.setOnClickListener{
                    seferDetail = 18
                }
                if (seferDetail==18){
                    binding.seferdetail18.isVisible=true
                    binding.btn18secim.setOnClickListener{
                        SeferVariables.btn_home_state = true
                        SeferVariables.sefer_number = 18
                    }
                }
                else{
                    binding.seferdetail18.isVisible=false
                }


                binding.btn19.setOnClickListener{
                    seferDetail = 19
                }
                if (seferDetail==19){
                    binding.seferdetail19.isVisible=true
                    binding.btn19secim.setOnClickListener{
                        SeferVariables.btn_home_state = true
                        SeferVariables.sefer_number = 19
                    }
                }
                else{
                    binding.seferdetail19.isVisible=false
                }



                binding.btn20.setOnClickListener{
                    seferDetail = 20
                }
                if (seferDetail==20){
                    binding.seferdetail20.isVisible=true
                    binding.btn20secim.setOnClickListener{
                        SeferVariables.btn_home_state = true
                        SeferVariables.sefer_number = 20
                    }
                }
                else{
                    binding.seferdetail20.isVisible=false
                }


                binding.btn21.setOnClickListener{
                    seferDetail = 21
                }
                if (seferDetail==21){
                    binding.seferdetail21.isVisible=true
                    binding.btn21secim.setOnClickListener{
                        SeferVariables.btn_home_state = true
                        SeferVariables.sefer_number = 21
                    }
                }
                else{
                    binding.seferdetail21.isVisible=false
                }


                binding.btn22.setOnClickListener{
                    seferDetail = 22
                }
                if (seferDetail==22){
                    binding.seferdetail22.isVisible=true
                    binding.btn22secim.setOnClickListener{
                        SeferVariables.btn_home_state = true
                        SeferVariables.sefer_number = 22
                    }
                }
                else{
                    binding.seferdetail22.isVisible=false
                }

                binding.btn23.setOnClickListener{
                    seferDetail = 23
                }
                if (seferDetail==23){
                    binding.seferdetail23.isVisible=true
                    binding.btn23secim.setOnClickListener{
                        SeferVariables.btn_home_state = true
                        SeferVariables.sefer_number = 23
                    }
                }
                else{
                    binding.seferdetail23.isVisible=false
                }


                binding.btn24.setOnClickListener{
                    seferDetail = 24
                }
                if (seferDetail==24){
                    binding.seferdetail24.isVisible=true
                    binding.btn24secim.setOnClickListener{
                        SeferVariables.btn_home_state = true
                        SeferVariables.sefer_number = 24
                    }
                }
                else{
                    binding.seferdetail24.isVisible=false
                }


                binding.btn25.setOnClickListener{
                    seferDetail = 25
                }
                if (seferDetail==25){
                    binding.seferdetail25.isVisible=true
                    binding.btn25secim.setOnClickListener{
                        SeferVariables.btn_home_state = true
                        SeferVariables.sefer_number = 25
                    }
                }
                else{
                    binding.seferdetail25.isVisible=false
                }

                handler.postDelayed(this, 1)
            }
        })
    }
}