package com.example.healdoc_mobile_5

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class MedicationAlarmActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences //디비대신에 저장

    private val medication_morning = "medication_morning"
    private val medication_noon = "medication_noon"
    private val medication_night = "medication_night"
    private var hMorning : Int = 0
    private var mMorning : Int = 0
    private var hNoon : Int = 0
    private var mNoon : Int = 0
    private var hNight : Int = 0
    private var mNight : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medication_alarm)

        sharedPreferences = getSharedPreferences("file_name", Context.MODE_PRIVATE) //file_name , 내부캐시 저장

        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = "복약 알리미"
        }

        timeh_morning.setText(sharedPreferences.getInt("morning_hour", 0).toString())
        timem_morning.setText(sharedPreferences.getInt("morning_minute", 0).toString())
        timeh_noon.setText(sharedPreferences.getInt("noon_hour", 0).toString())
        timem_noon.setText(sharedPreferences.getInt("noon_minute", 0).toString())
        timeh_night.setText(sharedPreferences.getInt("night_hour", 0).toString())
        timem_night.setText(sharedPreferences.getInt("night_minute", 0).toString())



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
        //왼쪽이 키 값을
        switch_medication_morning.isChecked = sharedPreferences.getBoolean(medication_morning, false)
        switch_medication_noon.isChecked = sharedPreferences.getBoolean(medication_noon, false)
        switch_medication_night.isChecked = sharedPreferences.getBoolean(medication_night, false)

        //아침 설정 스위치
        switch_medication_morning.setOnCheckedChangeListener { _, isChecked ->

            sharedPreferences.edit().apply {
                putBoolean(medication_morning, isChecked)
                apply()
            }

            if (isChecked){

                registerAlarm(Action.ALARM_MEDICATION_MORNING, hMorning, mMorning)
            }
            else {
                unregisterAlarm(Action.ALARM_MEDICATION_MORNING)
            }

        }

        //점심 설정 스위치
        switch_medication_noon.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit().apply {
                putBoolean(medication_noon, isChecked)
                apply()
            }

            if (isChecked) {
                registerAlarm(Action.ALARM_MEDICATION_NOON, hNoon, mNoon)
            }
            else{
                unregisterAlarm(Action.ALARM_MEDICATION_NOON)
            }
        }


        //저녁 설정 스위치
        switch_medication_night.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit().apply {
                putBoolean(medication_night, isChecked)
                apply()
            }

            if (isChecked){
                registerAlarm(Action.ALARM_MEDICATION_NIGHT, hNight, mNight)

            }
            else {
                unregisterAlarm(Action.ALARM_MEDICATION_NIGHT)
            }
        }


        //스위치 이미 on상태에서 시간만 변경된다면 스위치 걍 off시켜버리기
        //아침 시분
        timeh_morning.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                try{
                    hMorning = Integer.parseInt(timeh_morning.getText().toString())
                }
                catch(e:Exception){
                    hMorning = 0
                }

                sharedPreferences.edit().apply{
                    putInt("morning_hour", hMorning)
                    apply()
                }
                Log.d("AlarmAct", "Morning  hour  afterText!! : ${hMorning}")

            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("AlarmAct", "Morning    beforeTextChanged")
                if(switch_medication_morning.isChecked != false){
                    switch_medication_morning.isChecked = false
                }
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("AlarmAct", "Morning    onText!!!")
            }
        })
        timem_morning.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                try{
                    mMorning = Integer.parseInt(timem_morning.getText().toString())
                }
                catch (e:Exception){
                    mMorning = 0
                }
                sharedPreferences.edit().apply{
                    putInt("morning_minute", mMorning)
                    apply()
                }
                Log.d("AlarmAct", "Morning  minute  afterText!! : ${mMorning}")

            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("AlarmAct", "Morning    beforeTextChanged")
                if(switch_medication_morning.isChecked != false){
                    switch_medication_morning.isChecked = false
                }
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("AlarmAct", "Morning    onText!!!")
            }
        })

        //정오 시분
        timeh_noon.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                try{
                    hNoon = Integer.parseInt(timeh_noon.getText().toString())
                }
                catch(e:Exception){
                    mNoon = 0
                }
                sharedPreferences.edit().apply{
                    putInt("noon_hour", mNoon)
                    apply()
                }
                Log.d("AlarmAct", "Noon  hour  afterText!! : ${hNoon}")
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("AlarmAct", "noon  beforeTextChanged")
                if(switch_medication_noon.isChecked != false){
                    switch_medication_noon.isChecked = false
                }
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("AlarmAct", "noon  onText!!!")
            }
        })
        timem_noon.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                try{
                    mNoon = Integer.parseInt(timem_noon.getText().toString())
                }
                catch(e:Exception){
                    mNoon = 0
                }
                sharedPreferences.edit().apply{
                    putInt("noon_minute", mNoon)
                    apply()
                }
                Log.d("AlarmAct", "Morning  hour  afterText!! : ${mNoon}")
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("AlarmAct", "noon  beforeTextChanged")
                if(switch_medication_noon.isChecked != false){
                    switch_medication_noon.isChecked = false
                }
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("AlarmAct", "noon  onText!!!")
            }
        })

        //저녁 시분
        timeh_night.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                try{
                    hNight = Integer.parseInt(timeh_night.getText().toString())
                }
                catch(e:Exception){
                    hNight = 0
                }
                sharedPreferences.edit().apply{
                    putInt("night_hour", mNight)
                    apply()
                }
                Log.d("AlarmAct", "Noon  hour  afterText!! : ${hNight}")
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("AlarmAct", "Night beforeTextChanged")
                if(switch_medication_night.isChecked != false){
                    switch_medication_night.isChecked = false
                }
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("AlarmAct", "Night  onText!!!")
            }
        })
        timem_night.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                try{
                    mNight = Integer.parseInt(timem_night.getText().toString())
                }
                catch(e:Exception){
                    mNight = 0
                }
                sharedPreferences.edit().apply{
                    putInt("night_minute", mNight)
                    apply()
                }
                Log.d("AlarmAct", "Noon  hour  afterText!! : ${mNight}")

            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("AlarmAct", "Night  beforeTextChanged")
                if(switch_medication_night.isChecked != false){
                    switch_medication_night.isChecked = false
                }
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("AlarmAct", "Night  onText!!!")
            }
        })



    }


    private fun registerAlarm(actionName: String, hour: Int = 0, minute: Int = 0, second: Int = 0) {
        val alarmTime = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, second)
            //set(Calendar.HOUR_OF_DAY, Calendar.getInstance().get(Calendar.HOUR_OF_DAY))
            //set(Calendar.MINUTE, Calendar.getInstance().get(Calendar.MINUTE))
            //set(Calendar.SECOND, Calendar.getInstance().get(Calendar.SECOND) + 10)
        }
        Log.d("Medik", "register alarm! ${alarmTime.get(Calendar.HOUR_OF_DAY)}:${alarmTime.get(Calendar.MINUTE)}:${alarmTime.get(Calendar.SECOND)}")
        val intent = Intent(actionName)
            .putExtra("timestamp", alarmTime.timeInMillis) //24시간 후에 또 울릴 수 있도록 timestamp를 receive로
        val requestCode = 1234 //사용자가 원하는 숫자 아무거나
        //applicationContext: 전체적으로 돌고 있는 앱의 context,
        //requestCode: request가 여러개 있을 수 있는데 받는건 하나니까 그거 구분하려구
        //
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


