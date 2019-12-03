package com.example.healdoc_mobile_5


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home, null)

        val bookingButton = view.findViewById<Button>(R.id.btn_booking)

        bookingButton.setOnClickListener(object :View.OnClickListener {
            override fun onClick(v: View?) {
                val intent = Intent(context, BookingActivity::class.java)
                startActivity(intent)
            }
        })

        val drugButton = view.findViewById<Button>(R.id.btn_drug)

        drugButton.setOnClickListener(object :View.OnClickListener {
            override fun onClick(v: View?) {
                val intent = Intent(context, MapsActivity::class.java)
                startActivity(intent)
            }
        })
        return view
    }
}
