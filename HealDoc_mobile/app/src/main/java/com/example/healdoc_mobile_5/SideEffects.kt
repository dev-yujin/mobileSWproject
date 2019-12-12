package com.example.healdoc_mobile_5

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import com.example.healdoc_mobile_5.model.Pharm
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_record_se.*
import kotlinx.android.synthetic.main.activity_side_effects.*
import kotlinx.android.synthetic.main.activity_side_effects.toolbar
import java.util.ArrayList

class SideEffects : AppCompatActivity() {
    var pharmList = arrayListOf<Pharm>()
    var item : ArrayList<String> = arrayListOf()
    var newItem : ArrayList<String> = arrayListOf()
    var year : String = "none"
    var month : String = "none"
    var day : String = "none"
    var user = "홍길동"
    //val date = "2019년 1월 11일"
    private lateinit var pharmReference: DatabaseReference

    //var listView = findViewById<ListView>(R.id.listView)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_side_effects)

        setSupportActionBar(toolbar)

        if (intent.hasExtra("UserName")) {
            if(intent.getStringExtra("UserName") == " "){
                user = "홍길동"
            }
            else{
                user = intent.getStringExtra("UserName") //유저 이름 받아오기
            }
            Log.d("name!!!!!", "$user")
        } else{
            Log.d("RecordSeActivity", "전달된 사용자가 없습니다.")
        }

        pharmReference = FirebaseDatabase.getInstance().getReference("$user/처방전/")

        val pharmListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val key = snapshot.key
                    item.add(key!!)
                    year = key.substring(0,4)
                    month = key.substring(4,6)
                    day = key.substring(6,8)
                    newItem.add("${year}년 ${month}월 ${day}일")
                    Log.d("SideEffectsActivity", "${item}")
                }

                listView.adapter = ArrayAdapter(this@SideEffects,android.R.layout.simple_list_item_1,newItem)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("SideEffectsActivity", "loading db error!!!")
            }
        }

        listView.onItemClickListener = AdapterView.OnItemClickListener { parent, v, position, id ->

            val intent = Intent(this, RecordSeActivity::class.java)
            intent.putExtra("prescribed date",item[position])
            startActivity(intent)

            Log.d("SideEffects", "parent: $parent, v: $v, position: $position, id: $id")

        }

        pharmReference.addValueEventListener(pharmListener)


    }

}
