package com.example.healdoc_mobile_5

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_mypage.*

class MypageActivity : AppCompatActivity() , View.OnClickListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage)

        mypage_bt1.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.mypage_bt1 -> {
                val intent = Intent(this, SideEffects::class.java)
                startActivity(intent)
            }
        }
    }
}
