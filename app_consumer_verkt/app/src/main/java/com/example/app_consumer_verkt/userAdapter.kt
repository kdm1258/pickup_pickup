package com.example.app_consumer_verkt

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class userAdapter(val context: Context, val userlist: ArrayList<user>) : BaseAdapter() {
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
        val view: View = LayoutInflater.from(context).inflate(R.layout.list_user,null)
        val profile = view.findViewById<ImageView>(R.id.iv_bed)
        val name = view.findViewById<TextView>(R.id.tv_bed)

        val user = userlist[position]

        profile.setImageResource(user.profile)
        name.text = user.name

        return view
    }
}