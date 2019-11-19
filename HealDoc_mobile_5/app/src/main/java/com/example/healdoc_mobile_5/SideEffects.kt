package com.example.healdoc_mobile_5

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import com.example.healdoc_mobile_5.model.Pharm
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_side_effects.*
import java.util.ArrayList

class SideEffects : AppCompatActivity() {
    var pharmList = arrayListOf<Pharm>()
    var item : ArrayList<String> = arrayListOf()
    val person = "홍길동"
    val date = "2019년 1월 11일"
    private lateinit var pharmReference: DatabaseReference

    //val listView = findViewById<ListView>(R.id.listView)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_side_effects)

        pharmReference = FirebaseDatabase.getInstance().getReference("$person/")

        val pharmListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val key = snapshot.key
                    item.add(key!!)
                    Log.d("SideEffectsActivity", "${item}")
                }

                listView.adapter = ArrayAdapter(this@SideEffects,android.R.layout.simple_list_item_1,item)
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
