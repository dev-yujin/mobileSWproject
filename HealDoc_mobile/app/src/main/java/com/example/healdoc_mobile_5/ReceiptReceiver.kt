package com.example.healdoc_mobile_5

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.healdoc_mobile_5.model.Action

class ReceiptReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        context.startService(Intent(context, ReceiptService::class.java))
    }


}