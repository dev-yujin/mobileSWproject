package com.example.healdoc_mobile_5

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_booking_sel_date.*
import java.util.*

class BookingSelDateActivity : AppCompatActivity() {

    var y = 0 //년
    var m = 0 //월
    var d = 0 //일
    var h = " " //시
    //    var mi = 0 //분
    var tea = " " //선생님
    var hos = " " //병원
    val database : FirebaseDatabase = FirebaseDatabase.getInstance() //DB

    var cal = Calendar.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_sel_date)



        val myRef : DatabaseReference = database.getReference("예약목록")
//        myRef.setValue("안녕 반가워!")


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
            //showTimePicker()
            //선택할 수 없는 시간은 비활성화 해야

            //다른 액티비티의 요소를 사용하기 위함
//           var inflater = layoutInflater
//            var radioButton = inflater.inflate(R.layout.activity_timepicker__dialog, null)
//            var my = radioButton.findViewById<RadioButton>(R.id.btn_10)
//            my.isEnabled = false
//            Toast.makeText(this, "sssssssss", Toast.LENGTH_SHORT).show()


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
            //이미 존재하는 정보에는 쓸 수 없어야함
            myRef.child(hos).child(tea).child("${y}년${m}월${d}일").child(h).child("환자이름").setValue("김환자")
            myRef.child(hos).child(tea).child("${y}년${m}월${d}일").child(h).child("진료내용").setValue(edit_memo.text.toString())
            //--------DB
            var intent = Intent(this, BookingActivity::class.java)
            intent.putExtra("complet_book", "OK")
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

    }

    fun showDatePicker(){
        DatePickerDialog(this, DatePickerDialog.OnDateSetListener{datePicker, year, month, day ->
            y = year
            m = month + 1
            d = day

            //현재 날짜보다 작으면 선택할 수 없게 해야함

            view_date.text = "${year}년 ${month}월 ${day}일"
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

//    fun showTimePicker(){
//        TimePickerDialog(this, TimePickerDialog.OnTimeSetListener{timePicker, hour, minute ->
//            h = hour
//            mi = minute
//            view_time.text = "${hour}시 ${minute}분"
//        }, cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE), true).show()
//    }

}


