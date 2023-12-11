package com.example.project

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class Useradapter(val context: Context, val Userlist: ArrayList<User>, val companyname: String, val companyid: String) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.list_item_user, null)

        val name=view.findViewById<TextView>(R.id.nameTextview)
        val size=view.findViewById<TextView>(R.id.sizeTextview)
        val location=view.findViewById<TextView>(R.id.locationTextview)
        val number=view.findViewById<TextView>(R.id.numberTextview)
        val check=view.findViewById<Button>(R.id.check)
        val remove=view.findViewById<Button>(R.id.remove)

        val user=Userlist[position]

        val kind=user.kind
        val hash=user.hash
        val id=user.id
        val state=user.state
        name.text=user.name
        size.text=user.size
        location.text=user.location
        number.text=user.number

        check.setBackgroundResource(user.image)

        val database= Firebase.database.reference

        val reserveref=database.child("reserve").child(kind).child(hash)
        val userref=database.child("user").child(id).child("reservation").child(hash)

        check.setOnClickListener {
            if(state=="대기중") {
                reserveref.child("state").setValue(companyname)
                userref.child("state").setValue(companyname)
                userref.child("companyid").setValue(companyid)
            }
            else{
                reserveref.child("complete").setValue("true")
                userref.child("state").setValue("완료됨")
            }
        }
        remove.setOnClickListener {
            reserveref.setValue(null)
            userref.child("state").setValue("취소됨")
        }
        return view
    }

    override fun getItem(position: Int): Any {
        return Userlist[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return Userlist.size
    }
}