//예약하기 첫 화면 (진료과 선택, 선생님 선택)
package com.example.healdoc_mobile_5

//import android.support.v7.app.AppCompatActivity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.content.Intent
import kotlinx.android.synthetic.main.activity_booking.*

class BookingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)

        //날짜 선택하는 곳으로 넘기는 intent
        val intent = Intent(this, BookingSelDateActivity::class.java)

        //날짜선택 버튼 클릭 이벤트
        btn_sel_date.setOnClickListener {

            startActivity(intent)
        }


        var sel_sub : String? = ""
        var sel_tea : String? = ""

        //스피너 셋팅
        //val spinSubject = resources.getStringArray(R.array.subjectList)
        //val spinTeacher = resources.getStringArray(R.array.teacherList)
        val spinSubject = findViewById(R.id.spin_subject) as Spinner
        val spinTeacher = findViewById(R.id.spin_teacher) as Spinner
        val adapterSubject = ArrayAdapter.createFromResource(this, R.array.subjectList, R.layout.item_spinner)
        val adapterTeacher = ArrayAdapter.createFromResource(this, R.array.teacherList, R.layout.item_spinner)

        spinSubject.setSelection(0) //초기값
        spinTeacher.setSelection(0)

        //진료과목 선택 스피너
        spinSubject.adapter = adapterSubject
        spinSubject.prompt = "진료과목을 선택해주세요"
        spinSubject.onItemSelectedListener = object :AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                //string을 저장
                var temp : String = spinSubject.getItemAtPosition(position).toString()
                intent.putExtra("subIntent", temp)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        //선생님 선택
        spinTeacher.adapter = adapterTeacher
        spinTeacher.prompt = "선생님을 선택해주세요"
        spinTeacher.onItemSelectedListener = object :AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                //string을 저장
                var temp : String = spinTeacher.getItemAtPosition(position).toString()
                intent.putExtra("tchIntent", temp)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
    }




}
