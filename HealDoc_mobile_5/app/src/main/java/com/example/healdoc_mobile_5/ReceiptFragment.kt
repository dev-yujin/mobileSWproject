package com.example.healdoc_mobile_5


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_receipt.*

/**
 * A simple [Fragment] subclass.
 */
class ReceiptFragment : Fragment() {

    val database : FirebaseDatabase = FirebaseDatabase.getInstance() //DB
    var adapter : ListViewAdapter =ListViewAdapter()
    var user = "홍길동"

    var calledAlready = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!calledAlready)
        {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true); // 다른 인스턴스보다 먼저 실행되어야 한다.
            calledAlready = true;
        }

        // DB
        val myRef : DatabaseReference = database.getReference(user).child("예약")
        var today : String = "2019년12월1일" //오늘날짜
        var info : DatabaseReference
        var flag : Int = 1
        Log.w("myRef","${myRef.key}")

        if(myRef.child(today).key == today){
            info = myRef.child("2019년12월1일")
            flag = 1 //존재함
//            Log.w("myRef","hihihihihi")
        }
        else {
            info = myRef
            flag = 0 //존재하지 않음
        }

        info.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if(flag == 1) {

                    for (snapshot in p0.children) {
                        var post : bookInfo? = snapshot.getValue(bookInfo::class.java)
                        Log.w("COUNT","${snapshot.childrenCount}")
                        //오늘 날짜의 예약이 있으면 쓰기
                        adapter.addItem(today, "${post?.sub}", "${post?.hour}", "${post?.tea}")

                    }
                }
                adapter.notifyDataSetChanged() //어댑터에 리스트가 바뀜을 알린다

            }
        })

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_receipt, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        list_book.adapter = adapter

    }
}
