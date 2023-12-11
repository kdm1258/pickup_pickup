package com.example.app_consumer_verkt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var userlist = arrayListOf<user>(
        user(R.drawable.bed, "침대"),
        user(R.drawable.fan, "선풍기"),
        user(R.drawable.table, "책상"),
        user(R.drawable.ref, "냉장고"),
        user(R.drawable.bsofa, "소파"),
        user(R.drawable.monitor, "모니터"),
        user(R.drawable.mdrawer, "서랍"),
        user(R.drawable.bmirror, "유리/거울")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val id=intent.getStringExtra("id")

        val Adapter = userAdapter(this, userlist)
        listView.adapter = Adapter


        listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, _ ->
            val selectItem = parent.getItemAtPosition(position) as user

            val intent = Intent(this, ListActivity::class.java)
            intent.putExtra("kind", selectItem.name)
            intent.putExtra("id", id)
            startActivity(intent)
        }

        reserveButton.setOnClickListener{
            val intent = Intent(this, ReserveActivity::class.java)
            intent.putExtra("id", id)
            startActivity(intent)

            Toast.makeText(application, "확인", Toast.LENGTH_SHORT).show()
        }

    }
}
