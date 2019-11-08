package com.example.healdoc_mobile_5

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.android.synthetic.main.activity_booking_sel_date.*
import java.util.*

class BookingSelDateActivity : AppCompatActivity() {

    var y = 0 //년
    var m = 0 //월
    var d = 0 //일
    var h = 0 //시
    var mi = 0 //분
    var t = ""//선생님
    var hos = "" //병원

    var cal = Calendar.getInstance()

    private lateinit var database: DatabaseReference




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_sel_date)



        //DB : 새로운 예약 내역 생성
        database = FirebaseDatabase.getInstance().reference

        //선택한 진료과목 출력
        if(intent.hasExtra("subIntent")) {
            hos = intent.getStringExtra("subIntent")
            txt_subject.text = hos

//            database.child("users/예약/예약번호").setValue("123456799090")
//                .addOnSuccessListener {
//                    Toast.makeText(this, "업데이트 완료", Toast.LENGTH_SHORT).show()
//                }
//                .addOnFailureListener {
//                    Toast.makeText(this, "실패", Toast.LENGTH_SHORT).show()
//                }
            //DB : 삽입
        }
        else{
            Toast.makeText(this, "선택한 진료과목이 없습니다", Toast.LENGTH_SHORT).show()
        }

        //선택한 선생님 출력
        if(intent.hasExtra("tchIntent")) {
            t = intent.getStringExtra("tchIntent")
            txt_teacher.text = t
            //DB : 삽입
        }
        else{
            Toast.makeText(this, "선택한 선생님이 없습니다", Toast.LENGTH_SHORT).show()
        }


        //날짜 선택 버튼
        btn_sel_day.setOnClickListener {
            showDatePicker()
        }

        //시간 선택 버튼
        btn_sel_time.setOnClickListener {
            showTimePicker()
        }


    }


    fun showDatePicker(){
        DatePickerDialog(this, DatePickerDialog.OnDateSetListener{datePicker, year, month, day ->
            y = year
            m = month + 1
            d = day

            view_date.text = "${year}년 ${month}월 ${day}일"

            writeNewUser("${year}년 ${month}월 ${day}일",t,hos)
        }, cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DATE)).show();
    }

    fun showTimePicker(){
        TimePickerDialog(this, TimePickerDialog.OnTimeSetListener{timePicker, hour, minute ->
            h = hour
            mi = minute
            view_time.text = "${hour}시 ${minute}분"
        }, cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE), true).show()
    }


    @IgnoreExtraProperties
    data class User(
        var teacher: String? = "",
        var hospi: String? = ""
    )

    private fun writeNewUser(bookday: String, teacher: String, hos: String) {
        val user = User(teacher, hos)
        database.child("users").child(bookday).setValue(user)
    }

}


