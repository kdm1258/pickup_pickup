package com.example.app_consumer_verkt

import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class reservationAdapter (val context: Context, val userlist: ArrayList<reservation>) : BaseAdapter() {
    override fun getCount(): Int {
        return userlist.size
    }
    override fun getItem(position: Int): Any {
        return userlist[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.list_reservation,null)
        val kind = view.findViewById<TextView>(R.id.tv_kind)
        val company = view.findViewById<TextView>(R.id.tv_company)
        val adress = view.findViewById<TextView>(R.id.tv_adress)
        val time = view.findViewById<TextView>(R.id.tv_time)
        var check = view.findViewById<ImageView>(R.id.iv_check)

        val user = userlist[position]

        check.setImageResource(user.check)
        kind.text = user.kind
        company.text = user.company
        adress.text = user.adress
        time.text = user.time

        return view
    }
}