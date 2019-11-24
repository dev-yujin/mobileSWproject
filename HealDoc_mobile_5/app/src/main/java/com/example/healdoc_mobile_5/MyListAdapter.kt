package com.example.healdoc_mobile_5

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.EditText
import android.widget.ListView
import com.example.healdoc_mobile_5.model.SideMed
import kotlinx.android.synthetic.main.youngin_layout.view.*

class MyListAdapter(private val context: Context, private val list: ArrayList<SideMed>) : BaseAdapter(){
    var upSide : ArrayList<String> = arrayListOf()

    private val mInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getItem(position: Int): String = list[position].med_name//list[position]

    fun getText(position: Int) : String = list[position].side_text

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = list.size

    //fun getSideEffects(position: Int) : String = list[position].side_text

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val listitem = getItem(position)
        var side_t = getText(position)
        val view = mInflater.inflate(R.layout.youngin_layout, parent, false)

        view.TextView1.text = listitem
        view.editText.setText(side_t)

        view.setOnClickListener {
            it.editText.visibility = if (it.editText.visibility == EditText.VISIBLE){
                EditText.GONE
            } else{
                EditText.VISIBLE
            }
        }

        view.editText.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                //입력이 끝날 때 작동
                list[position].side_text = p0.toString()

            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //입력 하기 전에 작동
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //타이핑이 되는 동안 텍스트에 변화 있을 경우
            }
        })

        return view
    }
}