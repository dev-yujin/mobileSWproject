package com.example.healdoc_mobile_5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_medication_alarm.*

class MedicationAlarmActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medication_alarm)

        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = "복약 알리미"
        }
    }
}
