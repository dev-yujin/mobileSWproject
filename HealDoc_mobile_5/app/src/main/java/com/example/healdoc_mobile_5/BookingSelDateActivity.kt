package com.example.healdoc_mobile_5

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
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

        btn_reservation.isEnabled = false
        btn_sel_time.isEnabled = false
        setResult(RESULT_OK,intent)

        //뒤로가기 버튼 클릭시 -> activity 종료
        btn_back.setOnClickListener { finish() }

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
            btn_sel_time.isEnabled = true
        }

        //시간 선택 버튼
        btn_sel_time.setOnClickListener {
            //showTimePicker()

            var intent = Intent(this, timepicker_Dialog::class.java)
            startActivityForResult(intent,1)

        }

        //예약 완료 버튼
        btn_reservation.setOnClickListener {
            //여기서 DB에 정보 한꺼번에 업로드
            var intent = Intent(this, BookingActivity::class.java)
            intent.putExtra("complet_book", "OK")
            setResult(Activity.RESULT_OK, intent)
            finish()
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


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 1){
            if(resultCode == RESULT_OK) {
                var h = data?.getStringExtra("selecttime")
                view_time.text = h
//                    Toast.makeText(this, h, Toast.LENGTH_SHORT).show()
                if (h != " ") { //선택하면 버튼 활성화
                    btn_reservation.isEnabled = true
                }
            }
        }
    }

//    fun showTimePicker(){
//        TimePickerDialog(this, TimePickerDialog.OnTimeSetListener{timePicker, hour, minute ->
//            h = hour
//            mi = minute
//            view_time.text = "${hour}시 ${minute}분"
//        }, cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE), true).show()
//    }

}


