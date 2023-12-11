package com.example.project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var login_id:String=""
        var login_pass:String=""

        val database=Firebase.database.reference
        val loginRef=database.child("company")

        //회원가입
        button2.setOnClickListener{
            val intent = Intent(this, Signup::class.java)
            startActivity(intent)
        }

        //로그인
        button3.setOnClickListener {
            login_id = LOGIN_ID.text.toString()
            login_pass=LOGIN_PASS.text.toString()

            loginRef.child(login_id).get().addOnSuccessListener {
                //button2.text="${it.value.toString()} ${it.child("password").value} $login_pass"
                //var hashmap:HashMap<String, String> = it.value as HashMap<String, String>
                if(it.value.toString()!="null" && it.child("password").value.toString()==login_pass) {
                    val intent = Intent(this, Userlist::class.java)
                    intent.putExtra("kind", it.child("kind").value.toString())
                    intent.putExtra("name", it.child("name").value.toString())
                    intent.putExtra("id", login_id)
                    startActivity(intent)
                }
                else{
                    Toast.makeText(this, "로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener{
                Toast.makeText(this, "인터넷 환경을 확인해주세요", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
