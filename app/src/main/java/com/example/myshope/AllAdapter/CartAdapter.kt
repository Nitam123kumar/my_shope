package com.example.myshope.AllAdapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myshope.AllDataModel.AddCartDataModel
import com.example.myshope.R

class CartAdapter(val cartList: List<AddCartDataModel>): RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
    class CartViewHolder (itemView: View): RecyclerView.ViewHolder(itemView){

        val cartImageView=itemView.findViewById<ImageView>(R.id.cartImageView)
        val cartTittleTV=itemView.findViewById<TextView>(R.id.cartTittleTV)
        val cartTittleTV2=itemView.findViewById<TextView>(R.id.cartTittleTV2)
        val cartTittleTV3=itemView.findViewById<TextView>(R.id.cartTittleTV3)
        val cartPriceTV=itemView.findViewById<TextView>(R.id.cartPriceTV)
        val rating=itemView.findViewById<RatingBar>(R.id.rating)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view=
            LayoutInflater.from(parent.context).inflate(R.layout.cart_items_layout,parent,false)
        return CartViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
      val cartPosition=cartList[position]
        Glide.with(holder.itemView.context).load(cartPosition.cardImage).into(holder.cartImageView)
        holder.cartTittleTV.text=cartPosition.cardTittle
        holder.cartTittleTV2.text=cartPosition.cardDescription
        holder.cartTittleTV3.text=cartPosition.cardDescription2
        holder.cartPriceTV.text=cartPosition.cardProductPrice


    }
}