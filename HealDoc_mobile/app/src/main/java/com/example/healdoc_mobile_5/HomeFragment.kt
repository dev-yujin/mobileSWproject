package com.example.healdoc_mobile_5


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    val database : FirebaseDatabase = FirebaseDatabase.getInstance() //DB
    var user = "홍길동"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home, null)

        val bookingButton = view.findViewById<Button>(R.id.btn_booking)

        bookingButton.setOnClickListener(object :View.OnClickListener {
            override fun onClick(v: View?) {
                val intent = Intent(context, BookingActivity::class.java)
                startActivity(intent)
            }
        })

        val drugButton = view.findViewById<Button>(R.id.btn_drug)

        drugButton.setOnClickListener(object :View.OnClickListener {
            override fun onClick(v: View?) {
                val intent = Intent(context, MapsActivity::class.java)
                startActivity(intent)
            }
        })
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        connectFirebase()

    }

    open fun connectFirebase() {
        val myRef : DatabaseReference = database.getReference(user).child("예약")
        val compRef : DatabaseReference = database.getReference(user).child("진료완료")

        var now : LocalDate = LocalDate.now()
        var today = now.format(DateTimeFormatter.ofPattern("yyyy년 M월 d일")) //오늘날짜
        Log.w("today",today)

        //날짜를 비교하기 위함 - 가장 늦은 예약날짜를 출력할 것임
        var format : SimpleDateFormat = SimpleDateFormat("yyyy년 m월 d일")
        var day1 : Date = format.parse(today)

        //날짜를 비교해서 오늘 날짜에서 가장 가까운 날짜를 출력
        myRef.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                var day2: Date //snapshot 날짜
                var pday: Date  //프린트할 날짜를 담는 변수
                var compare: Int //날짜를 비교
                var r1: String? = "예약된 진료가 없습니다" //print할 예약된 날짜

                var cmax: Long = 1000 //날짜 차이를 계산할 변수
                for (snapshot in p0.children) {
                    day2 = format.parse(snapshot.key)
//                    Log.w("snapshot","${snapshot.key}")
                    pday = day1 //오늘 날짜로 셋팅
                    compare = pday.compareTo(day2) //오늘 날짜와 예약 날짜들 비교
                    if (compare > 0) {
                        //pday이 크다 . 오늘 날짜가 더 크면 띄울 날짜는 오늘
                        pday = day1
                        r1 = snapshot.key
                    } else if (compare < 0) {
                        //pday이 작다. 오늘 날짜가 더 작으면 띄울 날짜는 큰날
                        //두 날짜의 차이를 계산해서 오늘을 제외한 가장 가까운 날짜를 출력
                        var calDate: Long = day2.time - pday.time //초단위로 반환
                        calDate = calDate / (24 * 60 * 60 * 1000) //일 수로 변환
                        calDate = Math.abs(calDate)
                        Log.w("cmax", "${cmax}, $calDate")
                        if (calDate < cmax) {
                            //날짜의 차이가 작은겻을 셋팅
                            cmax = calDate
                            r1 = snapshot.key
                        }
                    } else {
                        //두 날짜가 같음. 같으면 아무거나
                    }
                }
                txt_booking_date?.text = r1 //최종 예약된 날짜 프린트

            }
        })

        //마지막 진료날짜 프린트
        compRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                var r2: String? = "받은 진료 내역이 없습니다" //print할 마지막 진료 날짜
                var day2: Date //snapshot 날짜
                var pday: Date  //프린트할 날짜를 담는 변수

                var cmax: Long = 1000 //날짜 차이를 계산할 변수
                Log.w("마지막 진료","${compRef.key}")
                for (snapshot in p0.children) {
                    day2 = format.parse(snapshot.key)
                    pday = day1 //오늘 날짜

                    var calDate: Long = day2.time - pday.time //초단위로 반환
                    Log.w("마지막 진료","${snapshot.key}")
                    calDate = calDate / (24 * 60 * 60 * 1000) //일 수로 변환
                    calDate = Math.abs(calDate)

                    if (calDate < cmax) {
                        //오늘 날짜와의 차이가 작은겻을 셋팅
                        cmax = calDate
                        r2 = snapshot.key
                    }
                }
                txt_last_date?.text = r2 //마지막 진료날짜 프린트
            }
        })

    }
}
