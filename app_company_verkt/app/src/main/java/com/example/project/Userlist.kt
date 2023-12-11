package com.example.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_userlist.*

class Userlist : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_userlist)

        val kind=intent.getStringExtra("kind").toString()

        val database=Firebase.database.reference
        val myref=database.child("reserve").child(kind)
        val context=this

        myref.addValueEventListener(
            object: ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val userlist=arrayListOf<User>()

                    for(i in dataSnapshot.children){
                        val key=i.key as String
                        val ref=dataSnapshot.child(key)
                        val state=ref.child("state").value.toString()
                        val name=intent.getStringExtra("name")
                        if(state=="대기중" || state==name) {

                            val image=if(ref.child("complete").value.toString()=="true") R.drawable.greycheck
                            else if(state==name) R.drawable.bluecheck
                            else R.drawable.check

                            userlist.add(
                                User(
                                    ref.child("name").value.toString(),
                                    ref.child("size").value.toString(),
                                    key,
                                    kind,
                                    ref.child("id").value.toString(),
                                    ref.child("number").value.toString(),
                                    ref.child("location").value.toString(),
                                    state,
                                    image,
                                )
                            )
                        }
                    }
                    val adapter=Useradapter(
                        context,
                        userlist,
                        intent.getStringExtra("name")!!,
                        intent.getStringExtra("id")!!
                    )
                    listView.adapter = adapter
                }
                override fun onCancelled(databaseError: DatabaseError) {
                    Toast.makeText(context, "인터넷 환경을 확인해주세요", Toast.LENGTH_SHORT).show()
                }
            }
        )

        val userlist=arrayListOf<User>(
            User("로딩중","로딩중", "", "", "", "", "", "", 0),
        )
        val adapter=Useradapter(context, userlist, "", "")
        listView.adapter = adapter

        listView.onItemClickListener= AdapterView.OnItemClickListener{parent, view, position, id ->
            val select=parent.getItemAtPosition(position) as User
            Toast.makeText(context, select.name, Toast.LENGTH_SHORT).show()
        }
    }
}