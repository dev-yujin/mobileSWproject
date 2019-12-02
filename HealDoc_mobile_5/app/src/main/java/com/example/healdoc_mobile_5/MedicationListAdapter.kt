package com.example.healdoc_mobile_5

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.healdoc_mobile_5.R
import com.example.healdoc_mobile_5.model.Pharm

class MedicationListAdapter(private val items: ArrayList<Pharm>) : RecyclerView.Adapter<MedicationListAdapter.ViewHolder>() {

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_medication, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = items[position].pharm_name
        holder.amount.text = items[position].amount
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.tv_name)
        val amount: TextView = view.findViewById(R.id.tv_amount)
    }
}