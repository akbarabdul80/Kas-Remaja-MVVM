package com.zerodev.kasremaja.ui.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zerodev.kasremaja.R
import com.zerodev.kasremaja.data.model.history.DataHistory
import kotlinx.android.synthetic.main.adapter_history.view.*

class HistoryAdapter(
    val data: List<DataHistory>
) :
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_history,
            parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.also {
            it.tvTitle.text = data[position].title_history
            it.tvDec.text = data[position].dec_history
            it.tvDate.text = data[position].date_history
        }
    }

    override fun getItemCount(): Int = data.size

}