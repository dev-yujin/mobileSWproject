package com.example.healdoc_mobile_5

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.EditText
import android.widget.ListView
import com.example.healdoc_mobile_5.model.SideMed
import kotlinx.android.synthetic.main.youngin_layout.view.*

class MyListAdapter(private val context: Context, private val list: ArrayList<SideMed>) : BaseAdapter(){

    private val mInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getItem(position: Int): String = list[position].med_name//list[position]

    fun getText(position: Int) : String = list[position].side_text

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = list.size

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val listitem = getItem(position)
        val side_t = getText(position)
        val view = mInflater.inflate(R.layout.youngin_layout, parent, false)

        view.setOnClickListener {
            it.editText.visibility = if (it.editText.visibility == EditText.VISIBLE){
                EditText.GONE
            } else{
                EditText.VISIBLE
            }
        }
        view.TextView1.text = listitem
        view.editText.setText(side_t)

        return view
    }
}