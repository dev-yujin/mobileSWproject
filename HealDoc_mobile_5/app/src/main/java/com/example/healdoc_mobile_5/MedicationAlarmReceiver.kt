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


        val alarmIntent = Intent(Action.ALARM_MEDICATION)
        val requestCode = 1234
        val pendingIntent = PendingIntent.getBroadcast(context, requestCode, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val alarmTime = intent?.extras?.getLong("timestamp") ?: System.currentTimeMillis()
        val dayInMillis = 24 * 60 * 60 * 1000

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.set(AlarmManager.RTC_WAKEUP, alarmTime + dayInMillis, pendingIntent)
    }

}