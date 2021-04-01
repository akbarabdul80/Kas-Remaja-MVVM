package com.zerodev.kasremaja.ui.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zerodev.kasremaja.R
import com.zerodev.kasremaja.data.model.brosur.DataBrosur
import kotlinx.android.synthetic.main.adapter_brosur.view.*

class DashboardAdapter(
    val data: List<DataBrosur>,
    val parent: DashboardInterfaces
) : RecyclerView.Adapter<DashboardAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_brosur,
            parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.also {
            it.tvBrosur.text = data[position].title_brosur
            it.tvSize.text = data[position].size_brosur
            it.tvDate.text = data[position].date_brosur

            it.setOnClickListener {
                parent.onCLick(data[position], position)
            }

            if (data[position].downloaded!!) {
                it.ivDownloaded.visibility = View.VISIBLE
            }
        }
    }

    override fun getItemCount(): Int = data.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}