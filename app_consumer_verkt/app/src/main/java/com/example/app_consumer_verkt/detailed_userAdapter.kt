package com.example.app_consumer_verkt

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class detailed_userAdapter (val context: Context, val detailed_userlist: ArrayList<detailed_user>) : BaseAdapter() {
    override fun getCount(): Int {
        return detailed_userlist.size
    }

    override fun getItem(position: Int): Any {
        return detailed_userlist[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.detailed_list,null)
        val profile = view.findViewById<ImageView>(R.id.iv_singlebed)
        val name = view.findViewById<TextView>(R.id.tv_singlesize)
        val size = view.findViewById<TextView>(R.id.tv_single_detailedsize)

        val user = detailed_userlist[position]

        profile.setImageResource(user.profile)
        name.text = user.name
        size.text = user.size

        return view
    }


}