package com.example.healdoc_mobile_5

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_mypage.*
import java.io.File

class SelectActivity (fn : FragmentManager) : FragmentPagerAdapter(fn) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0-> {
                HomeFragment()
            }
            1-> MypageFragment()
            else-> {
                return ReceiptFragment()
            }
            //this method is set out tabs positions
        }
    }

    override fun getCount(): Int {
        return 3
        // and this method will return 3 tabs
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0-> "Home"
            1-> "Mypage"
            else-> {
                return "Receipt"
            }
            // and this method will set out tabs titles
            // now move in main activity
        }
    }
}
