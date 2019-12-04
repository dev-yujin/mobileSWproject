package com.example.healdoc_mobile_5

import android.app.Notification
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.os.PowerManager
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import android.app.NotificationChannel
import android.graphics.Color


class ReceiptService : Service()  {

    override fun onBind(p0: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("Medik", "SOS")


        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        //오레오 이상부터
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val builder = Notification.Builder(this, "MEDICATION_ALARM_CHANNEL").apply {
                setSmallIcon(R.mipmap.healdoc_launcher)
                setContentText("진료시간이 다가옵니다. 진료실 앞에서 대기 해주세요.")
                setContentTitle("힐닥 접수 알림")
                setAutoCancel(true)
            }
            Toast.makeText(this, "alarm", Toast.LENGTH_LONG).show()
            notificationManager.notify(5000, builder.build())
        } else {
            //디자인 패턴 중에 빌더 패턴이 있는데 : 일일이 생성자 쓸 필요없이 빌더를 이용하여 필요한 부분만 설정한 뒤 빌더이용
            val builder = NotificationCompat.Builder(this, "MEDICATION_ALARM_CHANNEL").apply {
                setSmallIcon(R.mipmap.healdoc_launcher)
                setContentText("진료시간이 다가옵니다. 진료실 앞에서 대기 해주세요.")
                setContentTitle("힐닥 접수 알림")
                setAutoCancel(true)
            }
            Toast.makeText(this, "alarm2", Toast.LENGTH_LONG).show()
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