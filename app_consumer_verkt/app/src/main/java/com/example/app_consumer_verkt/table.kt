package com.example.app_consumer_verkt

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_fan.*
import kotlinx.android.synthetic.main.activity_table.*

class table : AppCompatActivity() {

    var detailed_userlist = arrayListOf<detailed_user>(
        detailed_user(R.drawable.foldingdesk, "접이식", ""),
        detailed_user(R.drawable.table , "일반", ""),
        detailed_user(R.drawable.desk, "대형", "")
    )
    //========== [동적 팝업창 전역 변수] ==========
    lateinit var alertDialog : AlertDialog
    lateinit var builder : AlertDialog.Builder

    //========== [동적 팝업창 호출 부분] ==========
    fun getAlertShow(){
        try{
            var str_tittle = "접수가 완료되었습니다."
            var str_message = "접수 확인 화면으로 이동할까요?"
            var str_buttonOK = "확인"
            var str_buttonNO = "취소"
            // var str_buttonNature = "이동"

            builder = AlertDialog.Builder(this@table)
            builder.setTitle(str_tittle) //팝업창 타이틀 지정
            builder.setIcon(R.drawable.alter) //팝업창 아이콘 지정
            builder.setMessage(str_message) //팝업창 내용 지정
            builder.setCancelable(false) //외부 레이아웃 클릭시도 팝업창이 사라지지않게 설정
            builder.setPositiveButton(str_buttonOK, DialogInterface.OnClickListener { dialog, which ->
                // TODO Auto-generated method stub
                val intent = Intent(this, MainActivity2::class.java)
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



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_table)


        val Adapter = detailed_userAdapter(this, detailed_userlist)
        lv_table.adapter = Adapter

        lv_table.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val selectItem = parent.getItemAtPosition(position) as detailed_user
//            selectItem 변수에 name값 저장 --> 이거 주소랑 같이 DB에 옮기면 될듯

            getAlertShow()
        }
    }
}