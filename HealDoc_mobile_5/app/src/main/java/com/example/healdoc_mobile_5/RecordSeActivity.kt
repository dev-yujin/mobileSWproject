package com.example.healdoc_mobile_5

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.view.OneShotPreDrawListener.add
import com.example.healdoc_mobile_5.model.Pharm
import com.example.healdoc_mobile_5.model.SideMed
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_booking_sel_date.*
import kotlinx.android.synthetic.main.activity_record_se.*
import kotlinx.android.synthetic.main.activity_record_se.toolbar
import kotlinx.android.synthetic.main.youngin_layout.view.*
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T



class RecordSeActivity : AppCompatActivity(){
    var date : String? = null
    val person : String = "홍길동"
    var item : ArrayList<SideMed> = arrayListOf()
    private lateinit var rec_pharms: DatabaseReference
    private lateinit var side_pharms: DatabaseReference
    val database : FirebaseDatabase = FirebaseDatabase.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record_se)
        //save_btn.setOnClickListener()

        setSupportActionBar(toolbar)
        val myRef : DatabaseReference = database.getReference("$person/부작용")
        //var rec_text = findViewById(R.id.rec_text) as TextView
        rec_listView.adapter = MyListAdapter(this@RecordSeActivity, item)

        if (intent.hasExtra("prescribed date")) {
            date = intent.getStringExtra("prescribed date")
            supportActionBar?.title = date
            Log.d("RecordSeActivity", "!!!!받은 date!!! : $date")
        } else {
            Toast.makeText(this, "not exist parcelable", Toast.LENGTH_SHORT).show()
        }

        rec_pharms = FirebaseDatabase.getInstance().getReference("$person/처방전/$date/복용약")
        val recListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val pharm = snapshot.getValue(Pharm::class.java)
                    item.add(
                        SideMed(med_name = pharm!!.pharm_name, side_text = "")
                        //Log.d("RecordSeActivity", "@@@med_name@@@ : ${it.med_name}")
                    )

                    //rec_listView.adapter = MyListAdapter(this@RecordSeActivity, item)
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("RecordSeActivity", "loading db error!!!")
            }

        }
        rec_pharms.addValueEventListener(recListener)

    //여기부터는 부작용 디비 연결할거야......
        side_pharms = FirebaseDatabase.getInstance().getReference("$person/부작용")
        val sideListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    item.forEach{
                        if (it.med_name == snapshot.key){
                            it.side_text = snapshot.getValue().toString()
                        }

                        Log.d("RecordSeActivity", "**side text** : ${it.side_text}")
                    }

                    rec_listView.invalidateViews()

                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("RecordSeActivity", "loading db error!!!")
            }

        }
        side_pharms.addValueEventListener(sideListener)


        save_btn.setOnClickListener {
            //여기서 DB에 정보 한꺼번에 업로드
            item.forEach{
                if(it.med_name != null && it.side_text!= ""){
                    myRef.child(it.med_name).setValue(it.side_text)
                }
            }

            Toast.makeText(this@RecordSeActivity, "저장되었습니다!", Toast.LENGTH_SHORT).show()
        }

    }


}
