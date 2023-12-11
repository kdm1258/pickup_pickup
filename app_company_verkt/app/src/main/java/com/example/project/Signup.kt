package com.example.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_signup.*

class Signup : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val database= Firebase.database.reference
        val myref=database.child("company")
        val context=this



        button.setOnClickListener{
            val name=nameInput.text.toString()
            val id=idInput.text.toString()
            val password=passInput.text.toString()
            val location=locationInput.text.toString()
            val kind=kindInput.text.toString()

            myref.child(id).get().addOnSuccessListener {
                if(it.value.toString()=="null"){
                    myref.child(id).child("name").setValue(name)
                    myref.child(id).child("password").setValue(password)
                    myref.child(id).child("location").setValue(location)
                    myref.child(id).child("kind").setValue(kind)

                    finish()
                }
                else{
                    Toast.makeText(context,"이미 가입된 아이디가 있습니다.",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}