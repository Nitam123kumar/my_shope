package com.example.myshope.AllAdapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myshope.AllDataModel.StatusDataModel
import com.example.myshope.R

class StatusAdapter(var statusList: ArrayList<StatusDataModel>) :
    RecyclerView.Adapter<StatusAdapter.StatusViewHolder>() {
    class StatusViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val statusImageView = itemView.findViewById<ImageView>(R.id.statusImageView)
        val statusTittleTextView = itemView.findViewById<TextView>(R.id.statusTittleTextView)


    }

    @SuppressLint("NotifyDataSetChanged")
    fun filteredList(womenProductsFilteredList: ArrayList<StatusDataModel>){
        statusList = womenProductsFilteredList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StatusViewHolder {
        val statusLayout =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_horizontal_layout, parent, false)
        return StatusViewHolder(statusLayout)
    }

    override fun onBindViewHolder(holder: StatusViewHolder, position: Int) {
        val status = statusList[position]
        Glide.with(holder.itemView.context).load(status.statusImage).into(holder.statusImageView)
        holder.statusTittleTextView.text = status.statusTittle
    }

    override fun getItemCount(): Int {
        return statusList.size

    }
}