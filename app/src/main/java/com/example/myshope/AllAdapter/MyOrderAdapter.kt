package com.example.myshope.AllAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myshope.AllDataModel.MyOrderDataModel
import com.example.myshope.R

class MyOrderAdapter(val list: ArrayList<MyOrderDataModel>):
    RecyclerView.Adapter<MyOrderAdapter.OrderViewHolder>() {
    class OrderViewHolder(item: View):RecyclerView.ViewHolder(item) {
        val orderImageView=item.findViewById<ImageView>(R.id.orderImageView)
        val orderTittle=item.findViewById<TextView>(R.id.orderTittle)
        val orderDescription=item.findViewById<TextView>(R.id.orderDescription)
        val orderPriceTV=item.findViewById<TextView>(R.id.orderPriceTV)
        val orderPriceTextView1=item.findViewById<TextView>(R.id.orderPriceTextView1)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val layout=
            LayoutInflater.from(parent.context).inflate(R.layout.my_order_layout,parent,false)
        return OrderViewHolder(layout)

    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val item=list[position]
        Glide.with(holder.itemView.context).load(item.orderImage).into(holder.orderImageView)
        holder.orderTittle.text=item.orderTittle
        holder.orderDescription.text=item.orderDescription
        holder.orderPriceTV.text=item.orderProductPrice
        holder.orderPriceTextView1.text=item.orderProductPrice

    }
}