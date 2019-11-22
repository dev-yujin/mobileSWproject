package com.example.healdoc_mobile_5

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import com.example.healdoc_mobile_5.R
import kotlinx.android.synthetic.main.activity_booking.*

class ReceiptActivity : AppCompatActivity() {

//    val LIST_MENU = arrayOf("LIST1", "LIST2", "LIST3") //리스트에 추가할 아이탬


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receipt)

//        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, LIST_MENU)

        val listview = findViewById(R.id.list_book) as ListView
        listview.adapter = MyAdapter(this)

        //뒤로가기 버튼 클릭시 -> activity 종료
        btn_back.setOnClickListener { finish() }


    }

    //어댑터 클래스
    private class MyAdapter(context: Context) : BaseAdapter() {
        private val mContext: Context

        private val bookDate = arrayListOf<String>(
            "2019.1.21", "2019.3.4", "2019.4.3"
        )

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
