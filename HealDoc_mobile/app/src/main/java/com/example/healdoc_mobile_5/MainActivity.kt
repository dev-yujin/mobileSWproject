package com.example.healdoc_mobile_5

import android.content.Intent
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.healdoc_mobile_5.ui.main.SectionsPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // first of all add design library.
        // now create an adapter class.

        val fragmentAdapter = SelectActivity(supportFragmentManager)
        view_pager.adapter = fragmentAdapter

        tabs.setupWithViewPager(view_pager)

        btn_QR.setOnClickListener(this)
        btn_mp.setOnClickListener(this) //마이페이지

        if (intent.hasExtra("UserName")) {
            user_name.text = intent.getStringExtra("UserName")
        } else {
            Toast.makeText(this, "전달된 이름이 없습니다", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onClick(view: View){
        when (view.id){
            R.id.btn_QR -> {
                val intent = Intent(this, QrReaderActivity::class.java)
                startActivity(intent)
            }
        }

        when (view.id){
            R.id.btn_booking -> {
                val intent = Intent(this, BookingActivity::class.java)
                startActivity(intent)
            }
        }

        when (view.id){
            R.id.btn_mp -> {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }
}