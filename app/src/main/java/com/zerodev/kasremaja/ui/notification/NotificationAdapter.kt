package com.zerodev.kasremaja.ui.notification

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zerodev.kasremaja.R
import com.zerodev.kasremaja.data.model.notification.DataNotification
import kotlinx.android.synthetic.main.adapter_notification.view.*

class NotificationAdapter(
    val data: List<DataNotification>
) : RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.adapter_notification,
                parent, false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.also {
            it.tvTitle.text = data[position].title
            it.tvDec.text = data[position].dec
            it.tvDate.text = data[position].date
        }

    }

    override fun getItemCount(): Int = data.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}