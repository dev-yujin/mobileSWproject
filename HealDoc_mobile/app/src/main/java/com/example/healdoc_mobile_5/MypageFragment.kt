package com.example.healdoc_mobile_5


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton

/**
 * A simple [Fragment] subclass.
 */
class MypageFragment : Fragment() {
    var user = "홍길동"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_mypage, null)



        //메인에서 유저이름을 받아와야한다.

        val prescriptButton = view.findViewById<ImageButton>(R.id.mypage_bt1)

        Log.e("setusermypage2", user)

        prescriptButton.setOnClickListener(object :View.OnClickListener {
            override fun onClick(v: View?) {
                val intent = Intent(context, SideEffects::class.java)
                intent.putExtra("UserName", user) //여기에 유저이름 넘겨야함
                startActivity(intent)
            }
        })

        val alarmMed_Btn = view.findViewById<ImageButton>(R.id.mypage_bt4)
        alarmMed_Btn.setOnClickListener(object :View.OnClickListener {
            override fun onClick(v: View?) {
                val intent = Intent(context, MedicationAlarmActivity::class.java)
                intent.putExtra("UserName", user) //여기에 유저이름 넘겨야함
                startActivity(intent)
            }
        })

        //예약내역 버튼
        val booking_Btn = view.findViewById<ImageButton>(R.id.mypage_book)
        booking_Btn.setOnClickListener(object :View.OnClickListener {
            override fun onClick(v: View?) {
                val intent = Intent(context, MypageBookList::class.java)
                intent.putExtra("UserName", user) //여기에 유저이름 넘겨야함
                startActivity(intent)
            }
        })

        //진료내역 버튼
        val complete_Btn = view.findViewById<ImageButton>(R.id.mypage_comp)
        complete_Btn.setOnClickListener(object :View.OnClickListener {
            override fun onClick(v: View?) {
                val intent = Intent(context, MypageCompleteList::class.java)
                intent.putExtra("UserName", user) //여기에 유저이름 넘겨야함
                startActivity(intent)
            }
        })

        return view
    }

    fun setuser(user : String){
        this.user = user
        Log.e("setusermypage", this.user)
    }
}
