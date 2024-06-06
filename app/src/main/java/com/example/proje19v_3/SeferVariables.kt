package com.example.proje19v_3

object SeferVariables {

    var location_data = doubleArrayOf(0.0,0.0)
    var currentLocation : Int = 0

    var fragment_state : Int = 0

    var mapState : Boolean = false


    var btn_home_state :Boolean = false
    var btn_seferSecim_state : Boolean = false
    var btn_iskAnons_state : Boolean = false
    var btn_otoAnons_state :Boolean = false
    var btn_gemiAnons_state: Boolean = false

    var visiblity_map : Boolean = true
    var visiblity_loc : Boolean = true
    var visiblity_vol : Boolean = true

    var announcement_val : Int = 0

    var otoAnonsTimer : Int = 0
    var otoAnonsState : Int = 0

    var playerState : Boolean = false
    var playerStateOto : Boolean = false
    var announcementRepeatNumber : Int = 2 // TEKRARLAMA
    var announcementDelayTime : Int = 20  // SANIYE
    var announcementCounter : Int = 0
    var playerVolume: Float = 0.75f

    var sefer_number : Int = 0
    var Uni_konum  = doubleArrayOf(40.814100804356414, 29.288378635040424,40.81709699829843, 29.294140020231218)
    var Ev_konum = doubleArrayOf(41.01320994453465, 29.02247559580991, 41.01378517610856, 29.023098102779393)
    var Uskudar_iskele = doubleArrayOf(41.0278426, 29.012778, 41.03078, 29.020130)
    var Besiktas_iskele = doubleArrayOf(41.03882, 29.00544, 41.04266, 29.01091)
    var Kabatas_iskele = doubleArrayOf(41.03291, 28.9926, 41.03619, 28.9958)
    var Kadikoy_iskele = doubleArrayOf(40.98950422, 29.014312, 40.994346, 29.02416177)
    var Eminonu_iskele = doubleArrayOf(41.0164801, 28.9736655, 41.020463, 28.98352794)
    var Kinaliada_iskele = doubleArrayOf(40.90838, 29.05284, 40.915, 29.05916)
    var Burgazada_iskele = doubleArrayOf(40.87904, 29.0669, 40.88674, 29.07481)
    var Heybeliada_iskele = doubleArrayOf(40.87324, 29.09428, 40.88226, 29.1069)
    var Buyukada_iskele = doubleArrayOf(40.87284, 29.1188, 40.87894, 29.13074)
    var Kuzguncuk_iskele = doubleArrayOf(41.03409, 29.02859, 41.03783, 29.03535)
    var Beylerbeyi_iskele = doubleArrayOf(41.04273, 29.04316, 41.04607, 29.05026)
    var Cengelkoy_iskele = doubleArrayOf(41.0497, 29.04969, 41.05224, 29.05351)
    var Kandilli_iskele = doubleArrayOf(41.07305, 29.05702, 41.0751, 29.06042)
    var Ahisari_iskele = doubleArrayOf(41.08226, 29.06475, 41.08426, 29.06796)
    var Arnavutkoy_iskele = doubleArrayOf(41.06601, 29.04229, 41.0676, 29.04454)
    var Bebek_iskele = doubleArrayOf(41.07464, 29.04223, 41.0777, 29.04616)
    var Emirgan_iskele = doubleArrayOf(41.10212, 29.0547, 41.10413, 29.05744)
    var Istinye_iskele = doubleArrayOf(41.11165, 29.05955, 41.11411, 29.06307)
    var Kanlica_iskele = doubleArrayOf(41.09931, 29.06432, 41.10102, 29.06667)
    var Kucuksu_iskele = doubleArrayOf(41.07818, 29.06375, 41.08025, 29.06724)
    var Ortakoy_iskele = doubleArrayOf(41.04626, 29.02211, 41.049, 29.02709)
    var Sariyer_iskele = doubleArrayOf(41.16483, 29.056, 41.16734, 29.05975)
    var Pasabahce_iskele = doubleArrayOf(41.11609, 29.08988, 41.11878, 29.09513)
    var Beykoz_iskele = doubleArrayOf(41.13285, 29.08706, 41.13629, 29.09313)
    var Cubuklu_iskele = doubleArrayOf(41.10709, 29.07817, 41.10937, 29.08202)
    var Akavagi_iskele = doubleArrayOf(41.17128, 29.08492, 41.1753, 29.092)
    var Rkavagi_iskele = doubleArrayOf(41.17958, 29.07215, 41.18378, 29.07884)
    var Buyukdere_iskele = doubleArrayOf(41.15838, 29.04275, 41.16324, 29.05007)

    var dolmabahcecamii_bturu = doubleArrayOf(41.03501, 28.99718, 41.03744, 28.99928)
    var dolmabahce_bturu = doubleArrayOf(41.03648, 29.00055, 41.03894, 29.00458)
    var ciragan_bturu = doubleArrayOf(41.04036, 29.01353, 41.04242, 29.01556)
    var feriye_bturu = doubleArrayOf(41.04156, 29.0173, 41.04543, 29.02053)
    var ortakoy_bturu = doubleArrayOf(41.0429, 29.02223, 41.04666, 29.02496)
    var ortakoycamii_bturu = doubleArrayOf(41.04417, 29.02566, 41.04682, 29.02836)
    var bogazicikoprusu_bturu = doubleArrayOf(41.04591, 29.02985, 41.04878, 29.03271)
    var galatasarayadasi_bturu = doubleArrayOf(41.05834, 29.03789, 41.06057, 29.04191)
    var arnavutkoy_bturu = doubleArrayOf(41.06102, 29.04083, 41.06301, 29.04503)
    var akoysirayalilar_bturu = doubleArrayOf(41.06446, 29.04339, 41.06634, 29.04751)
    var akintiburnu_bturu = doubleArrayOf(41.06766, 29.04631, 41.06983, 29.04915)
    var misirkonsolosluk_bturu = doubleArrayOf(41.07277, 29.04508, 41.0747, 29.04875)
    var bebek_bturu = doubleArrayOf(41.07649, 29.04431, 41.07908, 29.05085)
    var rumelihisari_bturu = doubleArrayOf(41.08268, 29.05594, 41.08476, 29.05979)
    var yusufpasakosku_bturu = doubleArrayOf(41.08826, 29.05719, 41.09003, 29.05992)
    var baltalimanihastanesi_bturu = doubleArrayOf(41.09462, 29.05383, 41.09626, 29.05802)
    var emirgan_bturu = doubleArrayOf(41.09768, 29.05452, 41.0989, 29.05795)
    var sakipsabancimuzesi_bturu = doubleArrayOf(41.10014, 29.05477, 41.10122, 29.05789)
    var kanlica_bturu = doubleArrayOf(41.09937, 29.06297, 41.10105, 29.06596)
    var fsmkoprusu_bturu = doubleArrayOf(41.09182, 29.0626, 41.09412, 29.06646)
    var anadoluhisari_bturu = doubleArrayOf(41.08431, 29.06452, 41.08589, 29.0674)
    var kucuksukasri_bturu = doubleArrayOf(41.07972, 29.06239, 41.08144, 29.06596)
    var kandilli_bturu = doubleArrayOf(41.07463, 29.05766, 41.07638, 29.06094)
    var vanikoy_bturu = doubleArrayOf(41.06831, 29.05337, 41.06973, 29.05741)
    var vanikoycamii_bturu = doubleArrayOf(41.06456, 29.05351, 41.06635, 29.05661)
    var kuleliaskerilisesi_bturu = doubleArrayOf(41.05843, 29.04998, 41.06055, 29.05334)
    var sumahan_bturu = doubleArrayOf(41.05549, 29.04961, 41.05665, 29.05294)
    var cengelkoy_bturu = doubleArrayOf(41.05047, 29.04821, 41.05242, 29.05193)
    var beylerbeyi_bturu = doubleArrayOf(41.04672, 29.04516, 41.04819, 29.04844)
    var beylerbeyisarayi_bturu = doubleArrayOf(41.04233, 29.03664, 41.04439, 29.04049)
    var kuzguncuk_bturu = doubleArrayOf(41.038, 29.02937, 41.04054, 29.03351)

}