package com.example.app_consumer_verkt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_reserve.*

class ReserveActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reserve)

        val database=Firebase.database.reference
        val myref=database.child("user").child(intent.getStringExtra("id")!!).child("reservation")

        val context=this
        myref.addValueEventListener(
            object: ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    var userlist=dataread(dataSnapshot)

                    val adapter=reservationAdapter(context, userlist)
                    lv_reservation.adapter = adapter
                }
                override fun onCancelled(databaseError: DatabaseError) {
                    Toast.makeText(context, "인터넷 환경을 확인해주세요", Toast.LENGTH_SHORT).show()
                }
            }
        )

        myref.get().addOnSuccessListener {
            var userlist=dataread(it)
            val adapter=reservationAdapter(context, userlist)
            lv_reservation.adapter = adapter
        }.addOnFailureListener{
            Toast.makeText(this, "인터넷 환경을 확인해주세요", Toast.LENGTH_SHORT).show()
        }

        lv_reservation.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->

                //리스트 클릭해서 상호작용할라면 여기에 쓰센

        }
    }

    fun dataread(dataSnapshot: DataSnapshot): ArrayList<reservation>{
        var userlist=arrayListOf<reservation>()

        for(i in dataSnapshot.children){
            val key=i.key as String
            val ref=dataSnapshot.child(key)

            val kind=ref.child("kind").value.toString()
            val size=ref.child("size").value.toString()
            val state=ref.child("state").value.toString()
            val check=  if(state=="대기중") R.drawable.graycircle
            else if(state=="완료됨") R.drawable.bluecircle
            else if(state=="취소됨") R.drawable.redcircle
            else R.drawable.greencircle
            val time=ref.child("time").value.toString()
            userlist.add(reservation(kind, time, check, size, state))
        }

        return userlist
    }
}