package com.example.healdoc_mobile_5

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.healdoc_mobile_5.model.Action


class MedicationAlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        Log.d("MedicarionAlarmReceiver","##### Alarm received: $context, $intent")
        context.startService(
            Intent(context, MedicationAlarmService::class.java)
        )


        val alarmIntent = Intent(Action.ALARM_MEDICATION)
        val requestCode = 1234
        val pendingIntent = PendingIntent.getBroadcast(context, requestCode, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val alarmTime = intent?.extras?.getLong("timestamp") ?: System.currentTimeMillis() //null일 수도 있으니까 엘비스
        val dayInMillis = 24 * 60 * 60 * 1000 //24시간을 밀리세컨드로

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.set(AlarmManager.RTC_WAKEUP, alarmTime + dayInMillis, pendingIntent) //하루 후에 또 울리라고 +해줌
    }

}