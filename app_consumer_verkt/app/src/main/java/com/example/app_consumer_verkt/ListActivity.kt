package com.example.app_consumer_verkt

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_list.*
import java.text.SimpleDateFormat
import java.util.*

class ListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        val id=intent.getStringExtra("id")!!
        val kind=intent.getStringExtra("kind")!!
        val database=Firebase.database.reference

        val detailed_usermap=mapOf(
            "책상" to arrayListOf<detailed_user>(
                detailed_user(R.drawable.foldingdesk, "접이식", ""),
                detailed_user(R.drawable.table , "일반", ""),
                detailed_user(R.drawable.desk, "대형", ""),
            ),

            "선풍기" to arrayListOf<detailed_user>(
                detailed_user(R.drawable.samllfan, "소형(캠핑용)", "(약 15cm ~ 20cm)"),
                detailed_user(R.drawable.fan, "중형(가정용)", "(약 20cm ~ 45cm)"),
                detailed_user(R.drawable.bigfan, "대형(공업용)", "(약 50cm ~ 90cm)"),
            ),

            "침대" to arrayListOf<detailed_user>(
                detailed_user(R.drawable.singlebed, "싱글사이즈", "(약 100 * 200)"),
                detailed_user(R.drawable.bed, "더블사이즈", "(약 135 * 190)"),
                detailed_user(R.drawable.queen, "퀸사이즈", "(약 150 * 200)"),
                detailed_user(R.drawable.kingsize, "킹사이즈", "(약 160 * 200)"),
                detailed_user(R.drawable.doubleking, "슈퍼킹사이즈", "(약 180 * 200)"),
            ),

            "냉장고" to arrayListOf<detailed_user>(
                detailed_user(R.drawable.sref, "소형", ""),
                detailed_user(R.drawable.bref, "대형", ""),
            ),

            "소파" to arrayListOf<detailed_user>(
                detailed_user(R.drawable.ssofa, "소형", ""),
                detailed_user(R.drawable.msofa, "중형", ""),
                detailed_user(R.drawable.bsofa, "대형", ""),
            ),

            "모니터" to arrayListOf<detailed_user>(
                detailed_user(R.drawable.monitor, "모니터", ""),
                detailed_user(R.drawable.tv, "티비", "")
            ),

            "서랍" to arrayListOf<detailed_user>(
                detailed_user(R.drawable.sdrawer, "소형", ""),
                detailed_user(R.drawable.mdrawer, "중형", ""),
                detailed_user(R.drawable.bdrawer, "대형", ""),
            ),

            "유리/거울" to arrayListOf<detailed_user>(
                detailed_user(R.drawable.mmirror, "중형", ""),
                detailed_user(R.drawable.bmirror, "대형", ""),
            ),
        )

        val Adapter = detailed_userAdapter(this, detailed_usermap.getValue(kind))
        ListViewSize.adapter = Adapter
        ListViewSize.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, _ ->
            Toast.makeText(this@ListActivity, "신청 완료", Toast.LENGTH_SHORT).show()
            val selectItem = parent.getItemAtPosition(position) as detailed_user
            val now: Long = System.currentTimeMillis()
            val date = Date(now)
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale("ko", "KR"))
            val stringTime = dateFormat.format(date)

            val ref = database.child("reserve").child(kind).child(now.toString())
            val myref = database.child("user").child(id)
            ref.child("size").setValue(selectItem.name)
            ref.child("time").setValue(stringTime)
            myref.get().addOnSuccessListener {
                //주문목록db
                ref.child("id").setValue(it.key.toString())
                ref.child("location").setValue(it.child("location").value.toString())
                ref.child("name").setValue(it.child("name").value.toString())
                ref.child("number").setValue(it.child("number").value.toString())
                ref.child("state").setValue("대기중")
            }

            val reservationref=myref.child("reservation").child(now.toString())
            //나의 db
            reservationref.child("kind").setValue(kind)
            reservationref.child("size").setValue(selectItem.name)
            reservationref.child("time").setValue(stringTime)
            reservationref.child("state").setValue("대기중")
             //selectItem 변수에 name값(소형, 중형, 대형) 저장 --> 이거 주소랑 같이 DB에 옮기면 될듯

            getAlertShow(id)
        }
    }

    lateinit var alertDialog : AlertDialog
    lateinit var builder : AlertDialog.Builder

    //========== [동적 팝업창 호출 부분] ==========
    fun getAlertShow(id: String){
        try{
            var str_tittle = "접수가 완료되었습니다."
            var str_message = "접수 확인 화면으로 이동할까요?"
            var str_buttonOK = "확인"
            var str_buttonNO = "취소"
            // var str_buttonNature = "이동"

            builder = AlertDialog.Builder(this@ListActivity)
            builder.setTitle(str_tittle) //팝업창 타이틀 지정
            builder.setIcon(R.drawable.alter) //팝업창 아이콘 지정
            builder.setMessage(str_message) //팝업창 내용 지정
            builder.setCancelable(false) //외부 레이아웃 클릭시도 팝업창이 사라지지않게 설정
            builder.setPositiveButton(str_buttonOK, DialogInterface.OnClickListener { dialog, which ->
                // TODO Auto-generated method stub
                val intent = Intent(this, ReserveActivity::class.java)
                intent.putExtra("id", id)
                startActivity(intent)

                Toast.makeText(application, "확인", Toast.LENGTH_SHORT).show()
            })
            builder.setNegativeButton(str_buttonNO, DialogInterface.OnClickListener { dialog, which ->
                // TODO Auto-generated method stub
                Toast.makeText(application, "취소", Toast.LENGTH_SHORT).show()
            })
            //builder.setNeutralButton(str_buttonNature, DialogInterface.OnClickListener { dialog, which ->
            // TODO Auto-generated method stub
            //  Toast.makeText(application, "이동", Toast.LENGTH_SHORT).show()
            //})
            alertDialog = builder.create()
            try {
                alertDialog.show()
            }
            catch (e : Exception){
                e.printStackTrace()
            }
        }
        catch(e : Exception){
            e.printStackTrace()
        }
    }
    //========== [동적 팝업창 닫기 부분] ==========
    fun getAlertHidden(){
        try {
            alertDialog.dismiss()
        }
        catch (e : Exception){
            e.printStackTrace()
        }
    }
}