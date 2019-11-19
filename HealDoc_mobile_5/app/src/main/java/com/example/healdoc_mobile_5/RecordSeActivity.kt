package com.example.healdoc_mobile_5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.*
import com.example.healdoc_mobile_5.model.Pharm
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_qr_reader.*
import kotlinx.android.synthetic.main.activity_record_se.*

class RecordSeActivity : AppCompatActivity() {
    var date : String? = null
    val person : String = "홍길동"
    var item : ArrayList<String> = arrayListOf()
    private lateinit var rec_pharms: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record_se)
        var rec_text = findViewById(R.id.rec_text) as TextView

        if (intent.hasExtra("prescribed date")) {
            date = intent.getStringExtra("prescribed date")
            rec_text.setText("$date")
            Log.d("RecordSeActivity", "!!!!받은 date!!! : $date")
        } else {
            Toast.makeText(this, "not exist parcelable", Toast.LENGTH_SHORT).show()
        }

        rec_pharms = FirebaseDatabase.getInstance().getReference("$person/$date/복용약")
        val recListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val pharm = snapshot.getValue(Pharm::class.java)
                    pharm?.let {
                        item.add(it.pharm_name)
                        Log.d("RecordSeActivity", "${item}")
                    }
                    rec_listView.adapter = ArrayAdapter(
                        this@RecordSeActivity,
                        android.R.layout.simple_list_item_1,
                        item
                    )
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("RecordSeActivity", "loading db error!!!")
            }

        }
        rec_pharms.addValueEventListener(recListener)
    }
}
