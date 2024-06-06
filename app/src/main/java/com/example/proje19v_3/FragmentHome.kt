package com.example.proje19v_3

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.proje19v_3.databinding.FragmentHomeBinding
import org.osmdroid.config.Configuration.*
import org.osmdroid.tileprovider.modules.OfflineTileProvider
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.tileprovider.tilesource.XYTileSource
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView


class FragmentHome : Fragment(R.layout.fragment_home){

    private val handler = Handler(Looper.getMainLooper())
    private lateinit var binding: FragmentHomeBinding
    private lateinit var announcementPlayer: AnnouncementPlayer
    private lateinit var map : MapView
    private var muteCounter: Int = 0
    private var muteState: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getInstance().load(requireContext(), PreferenceManager.getDefaultSharedPreferences(requireContext()))


        startLoop()
    }




    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)



        getInstance().load(requireContext(), PreferenceManager.getDefaultSharedPreferences(context))
        map = binding.frameMap
        val offlineTileProvider: OfflineTileProvider
        //offlineTileProvider.getMapTile()
        //OfflineTileProvider(XYTileSource("map", 11, 15, 256, ".sqlite", arrayOf("")))

        //map.tileProvider.tileSource = XYTileSource("map", 11, 15, 256, ".png", arrayOf(""))


        map.setTileSource(XYTileSource("map", 12, 15, 256, ".zip", arrayOf("")))
        map.setUseDataConnection(false)
        val mapController = map.controller
        mapController.setZoom(12)
        val startPoint = GeoPoint(41.8583, 29.2944);
        mapController.setCenter(startPoint);




        SeferVariables.fragment_state = 0
        announcementPlayer = AnnouncementPlayer(requireContext())


        if (SeferVariables.visiblity_map){
            binding.frameMap.isVisible = true
        }
        if (!SeferVariables.visiblity_map){
            binding.frameMap.isVisible = false
        }
        if (SeferVariables.visiblity_loc){
            binding.frameLoc.isVisible = true
        }
        if (!SeferVariables.visiblity_loc){
            binding.frameLoc.isVisible = false
        }
        if (SeferVariables.visiblity_vol){
            binding.frameVol.isVisible = true
        }
        if (!SeferVariables.visiblity_vol){
            binding.frameVol.isVisible = false
        }

        if(SeferVariables.otoAnonsState==1){
            when(SeferVariables.otoAnonsTimer){
                1-> binding.otoAnonsText.text = "2 DAKIKADA BIR"
                2-> binding.otoAnonsText.text = "5 DAKIKADA BIR"
                3-> binding.otoAnonsText.text = "10 DAKIKADA BIR"
                4-> binding.otoAnonsText.text = "15 DAKIKADA BIR"
                5-> binding.otoAnonsText.text = "20 DAKIKADA BIR"
                6-> binding.otoAnonsText.text = "30 DAKIKADA BIR"
                7-> binding.otoAnonsText.text = "45 DAKIKADA BIR"
                8-> binding.otoAnonsText.text = "60 DAKIKADA BIR"
            }
            binding.otoAnonsText.setTextColor(Color.GREEN)
        }
        if(SeferVariables.otoAnonsState==0){
            binding.otoAnonsText.text = "KAPALI"
            binding.otoAnonsText.setTextColor(Color.RED)
        }


        if (SeferVariables.sefer_number == 0){
            binding.btnSeferSecim.isVisible = true
            binding.textAnonsStateBool.text = "Devrede Değil"
            binding.textAnonsState.text = "Seçili Sefer yok"
            binding.textAnonsStateBool.setTextColor(Color.RED)
        }
        if (SeferVariables.sefer_number != 0){
            binding.textAnonsStateBool.text = "Devrede "
            binding.textAnonsStateBool.setTextColor(Color.GREEN)
            binding.btnSeferSecim.isVisible = false

            when(SeferVariables.sefer_number){
                1 -> binding.textSefer.text = "Kadıkoy-Beşiktaş"
                2 -> binding.textSefer.text = "Üsküdar-Beşiktaş"
                3 -> binding.textSefer.text = "Kadıkoy-Kabataş"
                4 -> binding.textSefer.text = "Üsküdar-Kabataş"
                5 -> binding.textSefer.text = "3 No Hisar Seferi Sabah Tarifesi"
                6 -> binding.textSefer.text = "3 No Hisar Seferi Akşam Tarifesi"
                7 -> binding.textSefer.text = "6 No Ortaboğaz Seferi Tarifesi"
                8 -> binding.textSefer.text = "6 No Ortaboğaz Seferi Pazar Tarifesi"
                9 -> binding.textSefer.text = "7 No Küçüksu Seferi Sabah Tarifesi"
                10 -> binding.textSefer.text = "7 No Küçüksu Seferi Akşam Tarifesi"
                11 -> binding.textSefer.text = "8 No Sarıyer Seferi Haftaiçi Sabah"
                12 -> binding.textSefer.text = "8 No Sarıyer Seferi Haftaiçi Akşam"
                13 -> binding.textSefer.text = "8 No Sarıyer Seferi Cumartesi Sabah"
                14 -> binding.textSefer.text = "8 No Sarıyer Seferi Cumartesi Akşam"
                15 -> binding.textSefer.text = "8 No Sarıyer Seferi Pazar Sabah"
                16 -> binding.textSefer.text = "9 No Beykoz Seferi Sabah"
                17 -> binding.textSefer.text = "9 No Beykoz Seferi Akşam"
                18 -> binding.textSefer.text = "14 No Şehir Hatları Adalar"
                19 -> binding.textSefer.text = "20 No Beykoz Seferi"
                20 -> binding.textSefer.text = "21 No Anadolu Kavağı"
                21 -> binding.textSefer.text = "23 No Beykoz Seferi"
                22 -> binding.textSefer.text = "24 No Sarıyer Seferi"
                23 -> binding.textSefer.text = "Eminönü-Beşiktaş-Ortaköy"
                24 -> binding.textSefer.text = "Kabataş-Çengelköy"
                25 -> binding.textSefer.text = "Dentur Adalar"

            }
        }

        binding.btnMute.setOnClickListener {
            binding.volBar.setProgress(0)
            announcementPlayer.release()
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

    override fun onDestroy() {

        handler.removeCallbacksAndMessages(null)
        super.onDestroy()
    }

    private fun startLoop() {
        handler.post(object : Runnable {
            override fun run() {

                binding.volBar.setProgress((SeferVariables.playerVolume*100).toInt())
                if (SeferVariables.playerVolume< 0.1){
                    binding.btnMute.text = "SESSIZDE"
                }
                else{
                    binding.btnMute.text = "SESSIZE AL"
                }

                binding.textLatVal.text = SeferVariables.location_data[0].toString()
                binding.textLngVal.text = SeferVariables.location_data[1].toString()

                SeferVariables.btn_seferSecim_state = binding.btnSeferSecim.isPressed==true
                SeferVariables.btn_otoAnons_state = binding.btnOtoAnons.isPressed==true
                if (SeferVariables.sefer_number != 0){
                    writeText()
                }
                handler.postDelayed(this, 10)
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun writeText(){
            when (SeferVariables.currentLocation) {
                0 -> binding.textAnonsState.text = "Anons Alanına Girmeyi Bekliyor..."
                1 -> binding.textAnonsState.text = "Üsküdar İskelesine Yanaşmaktayız"
                2 -> binding.textAnonsState.text = "Beşiktaş İskelesine Yanaşmaktayız"
                3 -> binding.textAnonsState.text = "Kabataş İskelesine Yanaşmaktayız"
                4 -> binding.textAnonsState.text = "Kadıköy İskelesine Yanaşmaktayız"
                5 -> binding.textAnonsState.text = "Eminönü İskelesine Yanaşmaktayız"
                6 -> binding.textAnonsState.text = "Kınalıada İskelesine Yanaşmaktayız"
                7 -> binding.textAnonsState.text = "Burgazada İskelesine Yanaşmaktayız"
                8 -> binding.textAnonsState.text = "Heybeliada İskelesine Yanaşmaktayız"
                9 -> binding.textAnonsState.text = "Büyükada İskelesine Yanaşmaktayız"
                10 -> binding.textAnonsState.text = "Kuzguncuk İskelesine Yanaşmaktayız"
                11 -> binding.textAnonsState.text = "Beylerbeyi İskelesine Yanaşmaktayız"
                12 -> binding.textAnonsState.text = "Çengelköy İskelesine Yanaşmaktayız"
                13 -> binding.textAnonsState.text = "Kandilli İskelesine Yanaşmaktayız"
                14 -> binding.textAnonsState.text = "Anadolu Hisarı İskelesine Yanaşmaktayız"
                15 -> binding.textAnonsState.text = "Arnavutköy İskelesine Yanaşmaktayız"
                16 -> binding.textAnonsState.text = "Bebek İskelesine Yanaşmaktayız"
                17 -> binding.textAnonsState.text = "Emirgan İskelesine Yanaşmaktayız"
                18 -> binding.textAnonsState.text = "İstinye İskelesine Yanaşmaktayız"
                19 -> binding.textAnonsState.text = "Kanlıca İskelesine Yanaşmaktayız"
                20 -> binding.textAnonsState.text = "Küçüksu İskelesine Yanaşmaktayız"
                21 -> binding.textAnonsState.text = "Ortaköy İskelesine Yanaşmaktayız"
                22 -> binding.textAnonsState.text = "Sarıyer İskelesine Yanaşmaktayız"
                23 -> binding.textAnonsState.text = "Paşabahçe İskelesine Yanaşmaktayız"
                24 -> binding.textAnonsState.text = "Beykoz İskelesine Yanaşmaktayız"
                25 -> binding.textAnonsState.text = "Çubuklu İskelesine Yanaşmaktayız"
                26 -> binding.textAnonsState.text = "Anadolu Kavağı İskelesine Yanaşmaktayız"
                27 -> binding.textAnonsState.text = "Rumeli Kavağı İskelesine Yanaşmaktayız"
                28 -> binding.textAnonsState.text = "Büyükdere İskelesine Yanaşmaktayız"

            }

    }



}