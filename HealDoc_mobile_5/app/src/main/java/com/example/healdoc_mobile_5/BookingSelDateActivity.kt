package com.example.healdoc_mobile_5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_booking_sel_date.*

class BookingSelDateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_sel_date)

        //선택한 진료과목 출력
        if(intent.hasExtra("subIntent")) {
            txt_subject.text = intent.getStringExtra("subIntent")
        }
        else{
            Toast.makeText(this, "전달된 진료과목이 없습니다", Toast.LENGTH_SHORT).show()
        }

        //선택한 선생님 출력
        if(intent.hasExtra("tchIntent")) {
            txt_teacher.text = intent.getStringExtra("tchIntent")
        }
        else{
            Toast.makeText(this, "전달된 선생님이 없습니다", Toast.LENGTH_SHORT).show()
        }


    }
}
