package com.example.healdoc_mobile_5

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.healdoc_mobile_5.R
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_booking.*

class ReceiptActivity : AppCompatActivity() {

    val database : FirebaseDatabase = FirebaseDatabase.getInstance() //DB

    var bookDate = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receipt)

        //DB
        val myRef : DatabaseReference = database.getReference("예약목록")
        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                for(snapshot in p0.children) {
                    if (snapshot.key.equals("이비인후과")) {
                        bookDate.add("20202020")
                        Toast.makeText(this@ReceiptActivity, "전달된 진료과목이 없습니다", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })


        //리스트 뷰
        val listview = findViewById(R.id.list_book) as ListView
        listview.adapter = MyAdapter(this)

        //뒤로가기 버튼 클릭시 -> activity 종료
        btn_back.setOnClickListener { finish() }



    }

    fun dbinit(){



    }

    //어댑터 클래스
    inner class MyAdapter(context: Context) : BaseAdapter() {
        private val mContext: Context



//        public val bookDate = arrayListOf<String>(
//            "2019.1.21", "2019.3.4", "2019.4.3"
//        )

        init{
            mContext = context
        }

        override fun getCount(): Int {
            return bookDate.size
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }
        override fun getItem(position: Int): Any {
            val selectItem = bookDate.get(position)
            return selectItem
        }

        override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View {

            val layoutInflater = LayoutInflater.from(mContext)
            val rowMain = layoutInflater.inflate(R.layout.list_book_item, viewGroup, false)

            val nameTextView = rowMain.findViewById<TextView>(R.id.txt_date)
            nameTextView.text = bookDate.get(position)
            val positionTextView = rowMain.findViewById<TextView>(R.id.txt_sub)
            positionTextView.text = "순서: " + position

            return rowMain
        }







    }
}
