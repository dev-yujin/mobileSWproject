package com.example.healdoc_mobile_5

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.healdoc_mobile_5.R
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_booking.*
import kotlinx.android.synthetic.main.activity_booking.btn_back
import kotlinx.android.synthetic.main.fragment_receipt.*

class ReceiptActivity : Fragment() {

    val database : FirebaseDatabase = FirebaseDatabase.getInstance() //DB

//    var bookDate = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.fragment_receipt)

        var user = "홍길동"

        val adapter : ListViewAdapter

        adapter = ListViewAdapter()

        //리스트 뷰
//        val listview = findViewById(R.id.list_book) as ListView
        list_book.adapter = adapter

        //DB
        val myRef : DatabaseReference = database.getReference(user).child("예약")
        var datedd : String = " "
        if(myRef.key.equals("2019년11월25일")){
            datedd = "2019년11월25일"
        }
        else
            datedd = "${myRef.key}"

        adapter.addItem(datedd,"정형외과","10;10","김유진")


        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                for(snapshot in p0.children) {
                    if (snapshot.key.equals("2019년11월25일")) {
//                        bookDate.add("2019년11월25일")
//                        adapter.addItem("2019년11월25일","정형외과","10;10","김유진")
//                        Toast.makeText(this@ReceiptActivity, "전달된 선생님이 없습니다", Toast.LENGTH_SHORT).show()

                   }
                }
            }
        })

        //클릭이벤트 처리
//        listview.onItemClickListener = AdapterView.OnItemClickListener { parent, v, position, id ->
//            // get item
//            val item = parent.getItemAtPosition(position) as ListViewItem
//
//            val title = item.title
//            val desc = item.desc
//            val icon = item.icon
//        }






        //뒤로가기 버튼 클릭시 -> activity 종료
        //프래그먼트라서 뒤로가기버튼 사용하지 않음!
//        btn_back.setOnClickListener { finish() }



    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        return super.onCreateView(inflater, container, savedInstanceState)

//        View view = inflater.inflate(R.layout.fragment_receipt, container, false)
        return inflater.inflate(R.layout.fragment_receipt, container, false)
    }


    //어댑터 클래스
//    inner class MyAdapter(context: Context) : BaseAdapter() {
//        private val mContext: Context
//
//
//
////        public val bookDate = arrayListOf<String>(
////            "2019.1.21", "2019.3.4", "2019.4.3"
////        )
//
//        init{
//            mContext = context
//        }
//
//        override fun getCount(): Int {
//            return bookDate.size
//        }
//
//        override fun getItemId(position: Int): Long {
//            return position.toLong()
//        }
//        override fun getItem(position: Int): Any {
//            val selectItem = bookDate.get(position)
//            return selectItem
//        }
//
//        override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View {
//
//            val layoutInflater = LayoutInflater.from(mContext)
//            val rowMain = layoutInflater.inflate(R.layout.list_book_item, viewGroup, false)
//
//            val nameTextView = rowMain.findViewById<TextView>(R.id.txt_date)
//            nameTextView.text = bookDate.get(position)
//            val positionTextView = rowMain.findViewById<TextView>(R.id.txt_sub)
//            positionTextView.text = "순서: " + position
//
//            return rowMain
//        }
//
//
//    }
}
