package com.example.healdoc_mobile_5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_medication_alarm.toolbar
import kotlinx.android.synthetic.main.activity_mypage_book_list.*

class MypageBookList : AppCompatActivity() {

    val database : FirebaseDatabase = FirebaseDatabase.getInstance() //DB
    var adapter : ListViewAdapter = ListViewAdapter()
    var user = "홍길동"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage_book_list)

        if (intent.hasExtra("UserName")) {
            user = intent.getStringExtra("UserName") //유저 이름 받아오기
            Log.e("MyPage_Booking유저이름!:",user)
        } else {
            Toast.makeText(this, "전달된 유저 이름이 없습니다", Toast.LENGTH_SHORT).show()
        }

        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = "예약 내역"
        }
        // DB
        val myRef : DatabaseReference = database.getReference(user).child("예약")

        //예약에서 다시 날짜 child로 들어가서 Listener사용 (예약목록 전체 읽어오기)
        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }
            override fun onDataChange(p0: DataSnapshot) {
                for (snapshot in p0.children) {
                    Log.w("ddddd" ,myRef.child("${snapshot.key}").key)
                    //dateRef = 예약 안에서 날짜 child에 접근
                    val dateRef : DatabaseReference = myRef.child("${snapshot.key}")
                    dateRef.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {

                        }
                        override fun onDataChange(p0: DataSnapshot) {
                            for (snapshot in p0.children) {
                                var post: bookInfo? = snapshot.getValue(bookInfo::class.java)
                                //어댑터에 리스트 요소 추가 (예약내역)
                                adapter.addItem(
                                    "${p0.key}",
                                    "${post?.sub}",
                                    "${post?.hour}",
                                    "${post?.tea}",
                                    "${snapshot.key}"
                                )
                            }
                            adapter.notifyDataSetChanged()//어댑터에 리스트가 바뀜을 알린다
                        }
                    })
                }
            }
        })
        list_comp.adapter = adapter //어댑터 연결
    }
}
