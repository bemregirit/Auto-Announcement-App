package com.example.proje19v_3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.proje19v_3.databinding.FragmentAnnouncementBinding


class FragmentAnnouncement : Fragment(R.layout.fragment_announcement) {

    private lateinit var binding: FragmentAnnouncementBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        SeferVariables.fragment_state = 1
        binding = FragmentAnnouncementBinding.inflate(inflater,container,false)

        binding.buttonIskeleAnons.setOnClickListener {
            SeferVariables.btn_iskAnons_state = true
        }
        binding.buttonSeferSecim.setOnClickListener {
            SeferVariables.btn_seferSecim_state = true
        }
        binding.buttonOtoAnons.setOnClickListener {
            SeferVariables.btn_otoAnons_state = true
        }
        binding.buttonGemiAnons.setOnClickListener {
            SeferVariables.btn_gemiAnons_state = true
        }

        return binding.root
    }

}