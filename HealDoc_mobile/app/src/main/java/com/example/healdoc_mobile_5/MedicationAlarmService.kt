package com.example.healdoc_mobile_5

import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.os.IBinder
import android.os.PowerManager
import android.util.Log
import androidx.core.app.NotificationCompat
import android.app.*
import android.R.attr.banner
import android.R.id.message
import android.graphics.BitmapFactory
import android.app.NotificationManager
import android.app.NotificationChannel
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T


class MedicationAlarmService : Service() {

    override fun onBind(p0: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("Medik", "SOS")
        val notificationManager =
            this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notificationId = 1
        val channelId = "channel-01"
        val channelName = "MEDICATION_AlARM_CHANNEL"
        val importance = NotificationManager.IMPORTANCE_HIGH

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val mChannel = NotificationChannel(
                channelId, channelName, importance
            )
            notificationManager.createNotificationChannel(mChannel)
        }

        val mBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.healdoc_launcher)
            .setContentTitle("힐닥 복용약 요정><")
            .setContentText("약 먹을 시간 입니다!!!!! 약 드세요 약!!!!!")

        notificationManager.notify(notificationId, mBuilder.build())


        //-------------------------------------------------------------------------

        // 화면 꺼져있을 때 화면 키기
        val powerManager = getSystemService(Context.POWER_SERVICE) as PowerManager
        if (!powerManager.isInteractive) {
            val wakeLock = powerManager.newWakeLock(
                PowerManager.FULL_WAKE_LOCK or
                        PowerManager.ACQUIRE_CAUSES_WAKEUP or
                        PowerManager.ON_AFTER_RELEASE, "HealDoc:WakeLock"
            )
            wakeLock.acquire(3000)
            val wakeLockCPU = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "HealDoc:lock")
            wakeLockCPU.acquire(3000)
        }

        return START_STICKY
    }

}