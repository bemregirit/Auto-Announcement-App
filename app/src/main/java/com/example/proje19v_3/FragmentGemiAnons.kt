package com.example.proje19v_3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.proje19v_3.databinding.FragmentGemiAnonsBinding
import com.example.proje19v_3.databinding.FragmentOtoAnonsBinding


class FragmentGemiAnons : Fragment() {

    private lateinit var binding: FragmentGemiAnonsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGemiAnonsBinding.inflate(inflater,container,false)

        binding.annNoSmoke.setOnClickListener{
            SeferVariables.announcement_val = 29
            Toast.makeText(requireContext(), "Sigara Anonsu Yapılıyor", Toast.LENGTH_LONG).show()
        }

        return binding.root
    }


}