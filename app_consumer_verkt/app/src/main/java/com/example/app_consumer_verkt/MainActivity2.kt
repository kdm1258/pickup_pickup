package com.example.app_consumer_verkt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import kotlinx.android.synthetic.main.activity_bed.*
import kotlinx.android.synthetic.main.activity_main2.*

class MainActivity2 : AppCompatActivity() {

    var kind = "dd"
    var time = " 1tl 1qns"
    var check = R.drawable.greencircle
//  var check = R.drawable.graycircle          업체에서 거절버튼 누르면 얘 쓰셈 위에꺼 주석처리하고
    var adress = "홍대"
    var company = "좆까회사"

    var userlist = arrayListOf<reservation>(
        reservation(kind, time, check, adress, company)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val Adapter = reservationAdapter(this, userlist)
        lv_reservation.adapter = Adapter

        lv_reservation.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->

           //리스트 클릭해서 상호작용할라면 여기에 쓰센

        }
    }
}