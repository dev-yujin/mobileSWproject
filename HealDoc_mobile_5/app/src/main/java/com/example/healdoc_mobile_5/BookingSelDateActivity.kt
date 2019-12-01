package com.example.healdoc_mobile_5

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_booking_sel_date.*
import java.util.*

class BookingSelDateActivity : AppCompatActivity() {

    var y = 0 //년
    var m = 0 //월
    var d = 0 //일
    var h = " " //시
    var tea = " " //선생님
    var hos = " " //병원
    val database : FirebaseDatabase = FirebaseDatabase.getInstance() //DB

    var user = "홍길동"//환자이름

    var cal = Calendar.getInstance()
    var count : Long = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_sel_date)



        val myRef : DatabaseReference = database.getReference("예약목록")
        val userRef : DatabaseReference = database.getReference(user)


        userRef.child("예약").child("${y}년${m}월${d}일").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                count= p0.getChildrenCount()
                Log.d("userRef","${count}")
            }
        })


        btn_reservation.isEnabled = false
        btn_sel_time.isEnabled = false
        setResult(RESULT_OK,intent)

        //뒤로가기 버튼 클릭시 -> activity 종료
        btn_back.setOnClickListener { finish() }



        //선택한 진료과목 출력
        if(intent.hasExtra("subIntent")) {
            hos = intent.getStringExtra("subIntent")
            txt_subject.text = hos

        }
        else{
            Toast.makeText(this, "전달된 진료과목이 없습니다", Toast.LENGTH_SHORT).show()
        }

        //선택한 선생님 출력
        if(intent.hasExtra("tchIntent")) {
            tea = intent.getStringExtra("tchIntent")
            txt_teacher.text = tea

        }
        else{
            Toast.makeText(this, "전달된 선생님이 없습니다", Toast.LENGTH_SHORT).show()
        }


        //날짜 선택 버튼
        btn_sel_day.setOnClickListener {
            showDatePicker()
            btn_sel_time.isEnabled = true
        }

        //시간 선택 버튼
        btn_sel_time.setOnClickListener {
            var intent = Intent(this, timepicker_Dialog::class.java)
            intent.putExtra("y",y)//년월일보내기
            intent.putExtra("m",m)
            intent.putExtra("d",d)
            intent.putExtra("tea",tea) //과목, 선생님 보내기
            intent.putExtra("hos",hos)
            startActivityForResult(intent,1)

        }

        //예약 완료 버튼
        btn_reservation.setOnClickListener {
            //여기서 DB에 정보 한꺼번에 업로드

            //예약목록을 위함
            myRef.child(hos).child(tea).child("${y}년${m}월${d}일").child(h).child("환자이름").setValue(user)
            myRef.child(hos).child(tea).child("${y}년${m}월${d}일").child(h).child("진료내용").setValue(edit_memo.text.toString())

            //user별로 예약상황을 update
            writeNewBook(h,tea,hos,edit_memo.text.toString())

            //--------DB
            var intent = Intent(this, BookingActivity::class.java)
            intent.putExtra("complet_book", "OK")
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

    }

    //유저/예약에 데이터 쓰기
    private fun writeNewBook(bh: String, bt: String, bs: String, bc: String) {

        val i = bookInfo(bh, bt, bs, bc)
        database.getReference(user).child("예약").child("${y}년${m}월${d}일").push().setValue(i)
    }


    fun showDatePicker(){
        DatePickerDialog(this, DatePickerDialog.OnDateSetListener{datePicker, year, month, day ->
            y = year
            m = month + 1
            d = day
            //현재 날짜보다 작으면 선택할 수 없게 해야함
            view_date.text = "${year}년 ${month+1}월 ${day}일"
        }, cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DATE)).show();
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 1){
            if(resultCode == RESULT_OK) {
                h = data!!.getStringExtra("selecttime")
                view_time.text = h
//                    Toast.makeText(this, h, Toast.LENGTH_SHORT).show()
                if (h != " ") { //선택하면 예약하기 버튼 활성화
                    btn_reservation.isEnabled = true
                }
            }
        }
    }

}


