package com.example.healdoc_mobile_5

//import android.support.v7.app.AppCompatActivity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener{



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_QR.setOnClickListener(this)
        btn_booking.setOnClickListener(this) //예약 버튼 리스너
    }

    override fun onClick(view: View){
        when (view.id){
            R.id.btn_QR -> {
                val intent = Intent(this, QrReaderActivity::class.java)
                startActivity(intent)
            }

        }
        when (view.id){
            R.id.btn_booking -> {
                val intent = Intent(this, BookingActivity::class.java)
                startActivity(intent)
            }
        }
    }

}
