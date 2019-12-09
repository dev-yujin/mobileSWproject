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
import android.widget.Toast


class MedicationAlarmService : Service() {

    override fun onBind(p0: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "channel-01"

        //오레오 이상
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { //Id 까지 적어주지 작동안함
            val builder = NotificationCompat.Builder(this, channelId).apply {
                setSmallIcon(R.drawable.ic_launcher_foreground) //mipmap 쓰면 작동 안함
                setContentText("복용약 알리미 요정")
                setContentTitle("복용약 알리미 요정")
                setPriority(NotificationCompat.PRIORITY_HIGH) //우선순위 높여주지 않으면 소리 안남
                setAutoCancel(true)
            }
            Toast.makeText(this, "복용약 알리미", Toast.LENGTH_LONG).show()

            assert(notificationManager != null)
            notificationManager.notify(5000, builder.build()) //동작시킴

        } else {
            //디자인 패턴 중에 빌더 패턴이 있는데 : 일일이 생성자 쓸 필요없이 빌더를 이용하여 필요한 부분만 설정한 뒤 빌더이용
            val builder = NotificationCompat.Builder(this, "MEDICATION_ALARM_CHANNEL").apply {
                setSmallIcon(R.mipmap.healdoc_launcher)
                setContentText("복용약 알리미 요정")
                setContentTitle("복용약 알리미 요정")
                setPriority(NotificationCompat.PRIORITY_HIGH)
                setAutoCancel(true)

            }
            Toast.makeText(this, "복용약 알리미", Toast.LENGTH_LONG).show()
            notificationManager.notify(8000, builder.build())
        }


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