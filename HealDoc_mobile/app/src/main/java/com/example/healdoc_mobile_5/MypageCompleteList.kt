package com.example.healdoc_mobile_5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_mypage_complete_list.*

class MypageCompleteList : AppCompatActivity() {

    val database : FirebaseDatabase = FirebaseDatabase.getInstance() //DB
    var adapter : ListViewAdapter = ListViewAdapter()
    var user = "홍길동"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage_complete_list)

        //Mypage fragment 에서 유저이름 받아오는 부분
        if (intent.hasExtra("UserName")) {
            user = intent.getStringExtra("UserName") //유저 이름 받아오기
            Log.e("MyPage_Comp유저이름!:",user)
        } else {
            Toast.makeText(this, "전달된 유저 이름이 없습니다", Toast.LENGTH_SHORT).show()
        }

        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = "진료 완료 내역"
        }
        // DB
        val myRef : DatabaseReference = database.getReference(user).child("진료완료")

        //진료완료에서 다시 날짜 child로 들어가서 Listener사용 (진료완료 목록 전체 읽어오기)
        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }
            override fun onDataChange(p0: DataSnapshot) {
                for (snapshot in p0.children) {
//                    Log.w("ddddd" ,myRef.child("${snapshot.key}").key)
                    //dateRef = 진료완료 안에서 날짜 child에 접근
                    val dateRef : DatabaseReference = myRef.child("${snapshot.key}")
                    dateRef.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {

                        }
                        override fun onDataChange(p0: DataSnapshot) {
                            for (snapshot in p0.children) {
                                var post: bookInfo? = snapshot.getValue(bookInfo::class.java)
                                //어댑터에 리스트 요소 추가 (진료완료 내역)
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
