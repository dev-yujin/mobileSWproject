package com.example.healdoc_mobile_5

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.healdoc_mobile_5.model.Action
import com.example.healdoc_mobile_5.model.Pharm
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_medication_alarm.*
import java.util.*
import kotlin.collections.ArrayList

class MedicationAlarmActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences //디비대신에 저장

    private val medication_morning = "medication_morning"
    private val medication_noon = "medication_noon"
    private val medication_night = "medication_night"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medication_alarm)

        sharedPreferences = getSharedPreferences("file_name", Context.MODE_PRIVATE) //file_name , 내부캐시 저장

        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = "복약 알리미"
        }

        val medications = ArrayList<Pharm>()
        medication_list.apply {
            layoutManager = LinearLayoutManager(this@MedicationAlarmActivity)
            adapter = MedicationListAdapter(medications)
        }

        //progress 보이고 시작!
        progressBar.visibility = ProgressBar.VISIBLE
        Log.d("MedicationAlarm", "!!!파베 연결 후, 아답터 전!!!")
        FirebaseDatabase.getInstance().getReference("홍길동/처방전/2019년 1월 11일/복용약")
            .addListenerForSingleValueEvent(object: ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    medications.addAll(
                        snapshot.children.mapNotNull { it.getValue(Pharm::class.java) }
                    )
                    medication_list.adapter?.notifyDataSetChanged()
                    Log.d("MedicationAlarm", "!!!파베 연결 후, 아답터 후!!!")
                    progressBar.visibility = ProgressBar.GONE
                    Log.d("MedicationAlarm", "<<<<<프로그레스 바 왜 안사라져>>>>>>>>")
                }


                override fun onCancelled(e: DatabaseError) {
                    Log.d("MedicationAlarm", "!!파베 연결 실패!!")
                    progressBar.visibility = ProgressBar.GONE
                }
            })

        // Switch
        switch_medication_morning.isChecked = sharedPreferences.getBoolean(medication_morning, false)
        switch_medication_noon.isChecked = sharedPreferences.getBoolean(medication_noon, false)
        switch_medication_night.isChecked = sharedPreferences.getBoolean(medication_night, false)

        //아침 설정 스위치
        switch_medication_morning.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit().apply {
                putBoolean(medication_morning, isChecked)
                apply()
            }

            if (isChecked)
                registerAlarm(Action.ALARM_MEDICATION_MORNING, 8)
            else
                unregisterAlarm(Action.ALARM_MEDICATION_MORNING)
        }

        //점심 설정 스위치
        switch_medication_noon.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit().apply {
                putBoolean(medication_noon, isChecked)
                apply()
            }

            if (isChecked)
                registerAlarm(Action.ALARM_MEDICATION_NOON, 12)
            else
                unregisterAlarm(Action.ALARM_MEDICATION_NOON)
        }

        //저녁 설정 스위치
        switch_medication_night.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit().apply {
                putBoolean(medication_night, isChecked)
                apply()
            }

            if (isChecked)
                registerAlarm(Action.ALARM_MEDICATION_NIGHT,18)
            else
                unregisterAlarm(Action.ALARM_MEDICATION_NIGHT)
        }
    }

    private fun registerAlarm(actionName: String, hour: Int = 0, minute: Int = 0, second: Int = 0) {
        val alarmTime = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, second)
        }
        val intent = Intent(actionName)
            .putExtra("timestamp", alarmTime.timeInMillis)
        val requestCode = 1234 //사용자가 원하는 숫자 아무거나
        val pendingIntent = PendingIntent.getBroadcast(applicationContext, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.set(AlarmManager.RTC_WAKEUP, alarmTime.timeInMillis, pendingIntent)
    }

    private fun unregisterAlarm(actionName: String) {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(actionName)
        val requestCode = 1234
        val pendingIntent = PendingIntent.getBroadcast(applicationContext, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        alarmManager.cancel(pendingIntent)
    }

}


