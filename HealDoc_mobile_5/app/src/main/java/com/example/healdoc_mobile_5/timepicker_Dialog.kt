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
                setResult(Activity.RESULT_OK,intent)
                Toast.makeText(this, "10", Toast.LENGTH_SHORT).show()
            }
            if(btn_11.isChecked){
                intent.putExtra("selecttime","11:00")
                Toast.makeText(this, "11", Toast.LENGTH_SHORT).show()
            }
            if(btn_12.isChecked){
                intent.putExtra("selecttime","12:00")
                Toast.makeText(this, "12", Toast.LENGTH_SHORT).show()
            }
            if(btn_13.isChecked){
                intent.putExtra("selecttime","13:00")
                Toast.makeText(this, "13", Toast.LENGTH_SHORT).show()
            }
            if(btn_14.isChecked){
                intent.putExtra("selecttime","14:00")
                Toast.makeText(this, "14", Toast.LENGTH_SHORT).show()
            }
            if(btn_15.isChecked){
                intent.putExtra("selecttime","15:00")
                Toast.makeText(this, "15", Toast.LENGTH_SHORT).show()
            }
            finish()

        }
    }
}
