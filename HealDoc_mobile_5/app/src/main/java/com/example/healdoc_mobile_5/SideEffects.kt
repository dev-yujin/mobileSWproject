package com.example.healdoc_mobile_5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import com.example.healdoc_mobile_5.model.Pharm
import com.google.firebase.database.*
import java.util.ArrayList

class SideEffects : AppCompatActivity() {
    var pharmList = arrayListOf<Pharm>()
    var item : MutableList<String> = arrayListOf()
    val person = "홍길동"
    private lateinit var pharmReference: DatabaseReference

//    val listView = findViewById<ListView>(R.id.listView)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_side_effects)

        pharmReference = FirebaseDatabase.getInstance().getReference("$person/복용약")

        val pharmListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (snapshot in dataSnapshot.children) {
                    val pharm = snapshot.getValue(Pharm::class.java)
                    pharm?.let {
                        pharmList.add(it)
                        item.add(it.pharm_name)
                        Log.d("SideEffectsActivity", "${item}")
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("SideEffectsActivity", "loading db error!!!")
            }

            //listView.adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,item)
        }

        pharmReference.addValueEventListener(pharmListener)


    }
}
