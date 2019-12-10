package com.example.healdoc_mobile_5

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_timepicker__dialog.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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

        //진료 정보 받아오기
        y = intent.getIntExtra("y", -1)
        m = intent.getIntExtra("m", -1)
        d = intent.getIntExtra("d", -1)
        tea = intent.getStringExtra("tea")
        hos = intent.getStringExtra("hos")

        if (intent.hasExtra("UserName")) {
            user = intent.getStringExtra("UserName") //유저 이름 받아오기
            Log.e("seldata유저이름!", user)
        } else {
            Toast.makeText(this, "전달된 유저 이름이 없습니다", Toast.LENGTH_SHORT).show()
        }



        //현재 시각 받아오기
        val current = LocalDateTime.now()


        var today = current.format(DateTimeFormatter.ofPattern("yyyy년 M월 d일")) //오늘날짜

//        if(today == "${y}년 ${m}월 ${d}일"){ //선택한 날짜가 오늘 날짜와 같으면 지난 시간은 예약할 수 없음
//            val list = listOf("10", "12", "14", "16", "18", "20")
//            val formatter = DateTimeFormatter.ofPattern("H")
//            val formatted = current.format(formatter)
//            Log.e("cur",formatted)
//
//            var com = formatted.compareTo("10")
//            Log.e("com","$com")
//
//            for(l in list){
//                var com = formatted.compareTo(l)
//                if(com >= 0 ){
//                }
//            }
//
//        }




        val myRef : DatabaseReference = database.getReference("예약목록").child(hos).child(tea).child("${y}년 ${m}월 ${d}일")
//        Toast.makeText(this, "${h}", Toast.LENGTH_SHORT).show()
        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) { //이미 예약이 되어있는 시간대이면 비활성화
                for(snapshot in p0.children){
                    //db의 예약목록 확인
                    if (snapshot.key.equals("10:00")) {
                        btn_10.isEnabled = false
                    }
                    if (snapshot.key.equals("12:00")) {
                        btn_12.isEnabled = false
                    }
                    if (snapshot.key.equals("14:00")) {
                        btn_14.isEnabled = false
                    }
                    if (snapshot.key.equals("15:00")) {
                        btn_16.isEnabled = false
                    }
                    if (snapshot.key.equals("18:00")) {
                        btn_18.isEnabled = false
                    }
                    if (snapshot.key.equals("20:00")) {
                        btn_20.isEnabled = false
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
                    if (snapshot.key.equals("12:00")) {
                        btn_12.isEnabled = false
                    }
                    if (snapshot.key.equals("14:00")) {
                        btn_14.isEnabled = false
                    }
                    if (snapshot.key.equals("16:00")) {
                        btn_16.isEnabled = false
                    }
                    if (snapshot.key.equals("18:00")) {
                        btn_18.isEnabled = false
                    }
                    if (snapshot.key.equals("20:00")) {
                        btn_20.isEnabled = false
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
            if(btn_12.isChecked){
                intent.putExtra("selecttime","12:00")
                setResult(RESULT_OK,intent)
            }
            if(btn_14.isChecked){
                intent.putExtra("selecttime","14:00")
                setResult(RESULT_OK,intent)
            }
            if(btn_16.isChecked){
                intent.putExtra("selecttime","16:00")
                setResult(RESULT_OK,intent)
            }
            if(btn_18.isChecked){
                intent.putExtra("selecttime","18:00")
                setResult(RESULT_OK,intent)
            }
            if(btn_20.isChecked){
                intent.putExtra("selecttime","20:00")
                setResult(RESULT_OK,intent)
            }
            finish()

        }
    }
}
