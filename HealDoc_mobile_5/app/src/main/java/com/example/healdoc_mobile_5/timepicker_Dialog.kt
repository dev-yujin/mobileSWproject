package com.example.healdoc_mobile_5

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_timepicker__dialog.*

class timepicker_Dialog : AppCompatActivity() {

    var y = 0 //년
    var m = 0 //월
    var d = 0 //일
    //    var mi = 0 //분
    var tea = " " //선생님
    var hos = " " //병원

    var user = "홍길동" //user이름
    val database : FirebaseDatabase = FirebaseDatabase.getInstance() //DB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timepicker__dialog)


        y = intent.getIntExtra("y", -1)
        m = intent.getIntExtra("m", -1)
        d = intent.getIntExtra("d", -1)
        tea = intent.getStringExtra("tea")
        hos = intent.getStringExtra("hos")

        val myRef : DatabaseReference = database.getReference("예약목록").child(hos).child(tea).child("${y}년${m}월${d}일")
//        Toast.makeText(this, "${h}", Toast.LENGTH_SHORT).show()
        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) { //이미 예약이 되어있는 시간대이면 비활성화
                for(snapshot in p0.children){
                    if (snapshot.key.equals("10:00")) {
                        btn_10.isEnabled = false
                    }
                    if (snapshot.key.equals("11:00")) {
                        btn_11.isEnabled = false
                    }
                    if (snapshot.key.equals("12:00")) {
                        btn_12.isEnabled = false
                    }
                    if (snapshot.key.equals("13:00")) {
                        btn_13.isEnabled = false
                    }
                    if (snapshot.key.equals("14:00")) {
                        btn_14.isEnabled = false
                    }
                    if (snapshot.key.equals("15:00")) {
                        btn_15.isEnabled = false
                    }
                }

            }
        })

        //유저가 그 날짜 그 시간에 다른 병원예약이 있으면, ..예약 불가능
        val userRef : DatabaseReference = database.getReference(user).child("예약").child("${y}년${m}월${d}일")

        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) { //이미 예약이 되어있는 시간대이면 비활성화
                for(snapshot in p0.children){
                    if (snapshot.key.equals("10:00")) {
                        btn_10.isEnabled = false
                    }
                    if (snapshot.key.equals("11:00")) {
                        btn_11.isEnabled = false
                    }
                    if (snapshot.key.equals("12:00")) {
                        btn_12.isEnabled = false
                    }
                    if (snapshot.key.equals("13:00")) {
                        btn_13.isEnabled = false
                    }
                    if (snapshot.key.equals("14:00")) {
                        btn_14.isEnabled = false
                    }
                    if (snapshot.key.equals("15:00")) {
                        btn_15.isEnabled = false
                    }
                }

            }
        })

        var intent = Intent(this, BookingSelDateActivity::class.java)


        button.setOnClickListener {
            if(btn_10.isChecked){
                intent.putExtra("selecttime","10:00")
                setResult(RESULT_OK,intent)
            }
            if(btn_11.isChecked){
                intent.putExtra("selecttime","11:00")
                setResult(RESULT_OK,intent)
            }
            if(btn_12.isChecked){
                intent.putExtra("selecttime","12:00")
                setResult(RESULT_OK,intent)
            }
            if(btn_13.isChecked){
                intent.putExtra("selecttime","13:00")
                setResult(RESULT_OK,intent)
            }
            if(btn_14.isChecked){
                intent.putExtra("selecttime","14:00")
                setResult(RESULT_OK,intent)
            }
            if(btn_15.isChecked){
                intent.putExtra("selecttime","15:00")
                setResult(RESULT_OK,intent)
            }
            finish()

        }
    }
}
