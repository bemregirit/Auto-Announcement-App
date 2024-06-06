package com.example.proje19v_3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.proje19v_3.databinding.FragmentIskAnonsBinding


class FragmentIskAnons : Fragment(R.layout.fragment_isk_anons) {

    private lateinit var binding: FragmentIskAnonsBinding

    private lateinit var announcementPlayer: AnnouncementPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentIskAnonsBinding.inflate(inflater,container,false)
        announcementPlayer = AnnouncementPlayer(requireContext())

        println("Anons Yapma Ekranındasın")

        binding.btn1.setOnClickListener{
            SeferVariables.announcement_val = 1
        }
        binding.btn2.setOnClickListener{
            SeferVariables.announcement_val = 2
        }
        binding.btn3.setOnClickListener{
            SeferVariables.announcement_val = 3
        }
        binding.btn4.setOnClickListener{
            SeferVariables.announcement_val = 4
        }
        binding.btn5.setOnClickListener{
            SeferVariables.announcement_val = 5
        }
        binding.btn6.setOnClickListener{
            SeferVariables.announcement_val = 6
        }
        binding.btn7.setOnClickListener{
            SeferVariables.announcement_val = 7
        }
        binding.btn8.setOnClickListener{
            SeferVariables.announcement_val = 8
        }
        binding.btn9.setOnClickListener{
            SeferVariables.announcement_val = 9
        }
        binding.btn10.setOnClickListener{
            SeferVariables.announcement_val = 10
        }
        binding.btn11.setOnClickListener{
            SeferVariables.announcement_val = 11
        }
        binding.btn12.setOnClickListener{
            SeferVariables.announcement_val = 12
        }
        binding.btn13.setOnClickListener{
            SeferVariables.announcement_val = 13
        }
        binding.btn14.setOnClickListener{
            SeferVariables.announcement_val = 14
        }
        binding.btn15.setOnClickListener{
            SeferVariables.announcement_val = 15
        }
        binding.btn16.setOnClickListener{
            SeferVariables.announcement_val = 16
        }
        binding.btn17.setOnClickListener{
            SeferVariables.announcement_val = 17
        }
        binding.btn18.setOnClickListener{
            SeferVariables.announcement_val = 18
        }
        binding.btn19.setOnClickListener{
            SeferVariables.announcement_val = 19
        }
        binding.btn20.setOnClickListener{
            SeferVariables.announcement_val = 20
        }
        binding.btn21.setOnClickListener{
            SeferVariables.announcement_val = 21
        }
        binding.btn22.setOnClickListener{
            SeferVariables.announcement_val = 22
        }
        binding.btn23.setOnClickListener{
            SeferVariables.announcement_val = 23
        }
        binding.btn24.setOnClickListener{
            SeferVariables.announcement_val = 24
        }
        binding.btn25.setOnClickListener{
            SeferVariables.announcement_val = 25
        }
        binding.btn26.setOnClickListener{
            SeferVariables.announcement_val = 26
        }
        binding.btn27.setOnClickListener{
            SeferVariables.announcement_val = 27
        }
        binding.btn28.setOnClickListener{
            SeferVariables.announcement_val = 28
        }


        return binding.root
    }



}