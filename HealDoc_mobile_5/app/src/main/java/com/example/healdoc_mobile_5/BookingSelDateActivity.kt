package com.example.healdoc_mobile_5

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_booking_sel_date.*
import java.util.*

class BookingSelDateActivity : AppCompatActivity() {

    var y = 0 //년
    var m = 0 //월
    var d = 0 //일
    var h = 0 //시
    var mi = 0 //분

    var cal = Calendar.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_sel_date)

        //DB : 새로운 예약 내역 생성

        //선택한 진료과목 출력
        if(intent.hasExtra("subIntent")) {
            txt_subject.text = intent.getStringExtra("subIntent")
            //DB : 삽입
        }
        else{
            Toast.makeText(this, "전달된 진료과목이 없습니다", Toast.LENGTH_SHORT).show()
        }

        //선택한 선생님 출력
        if(intent.hasExtra("tchIntent")) {
            txt_teacher.text = intent.getStringExtra("tchIntent")
            //DB : 삽입
        }
        else{
            Toast.makeText(this, "전달된 선생님이 없습니다", Toast.LENGTH_SHORT).show()
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
        }, cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DATE)).show();
    }

    fun showTimePicker(){
        TimePickerDialog(this, TimePickerDialog.OnTimeSetListener{timePicker, hour, minute ->
            h = hour
            mi = minute
            view_time.text = "${hour}시 ${minute}분"
        }, cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE), true).show()
    }

}


