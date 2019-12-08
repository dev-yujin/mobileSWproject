package com.example.healdoc_mobile_5

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.os.PowerManager
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.fragment_receipt.*

//import sun.jvm.hotspot.utilities.IntArray


class ReceiptService : Service()  {

    override fun onBind(p0: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        //오레오 이상
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val builder = NotificationCompat.Builder(this, "MEDICATION_ALARM_CHANNEL").apply {
                setSmallIcon(R.mipmap.healdoc_launcher)
                setContentText("진료시간이 다가옵니다. 진료실 앞에서 대기 해주세요.")
                setContentTitle("힐닥 접수 알림")
                setAutoCancel(true)
            }

            //오레오는 체널을 등록 해줘야함
//            builder.setSmallIcon(R.drawable.ic_launcher_foreground)
            val channelName = "노티피케이션 채널"
            val description = "오레오 이상을 위한 것임"
            val importance = NotificationManager.IMPORTANCE_HIGH

            val channel = NotificationChannel("2020", channelName, importance)
            channel.description = description

            assert(notificationManager != null)
            notificationManager.createNotificationChannel(channel) //체널 등록

            Toast.makeText(this, "진료시간이 다가옵니다. 진료실 앞에서 대기 해주세요.", Toast.LENGTH_LONG).show()

            assert(notificationManager != null)
            notificationManager.notify(5000, builder.build()) //동작시킴

            //++



            //


        } else {
            //디자인 패턴 중에 빌더 패턴이 있는데 : 일일이 생성자 쓸 필요없이 빌더를 이용하여 필요한 부분만 설정한 뒤 빌더이용
            val builder = NotificationCompat.Builder(this, "MEDICATION_ALARM_CHANNEL").apply {
                setSmallIcon(R.mipmap.healdoc_launcher)
                setContentText("진료시간이 다가옵니다. 진료실 앞에서 대기 해주세요.")
                setContentTitle("힐닥 접수 알림")
                setAutoCancel(true)
            }
            Toast.makeText(this, "진료시간이 다가옵니다. 진료실 앞에서 대기 해주세요.", Toast.LENGTH_LONG).show()
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