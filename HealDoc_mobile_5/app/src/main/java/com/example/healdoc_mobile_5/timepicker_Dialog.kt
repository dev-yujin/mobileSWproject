package com.example.healdoc_mobile_5

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_timepicker__dialog.*

class timepicker_Dialog : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timepicker__dialog)

        val intent = Intent(this, BookingSelDateActivity::class.java)

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
