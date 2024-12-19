package com.example.myshope.AllAdapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myshope.AllDataModel.womenProductsDataModel
import com.example.myshope.OnClickInterface.ProductsDetailsOnclick
import com.example.myshope.R

class TrendingProductsAdapter(var trendingProductsList: ArrayList<womenProductsDataModel>,var productDetailsOnclick: ProductsDetailsOnclick) :
    RecyclerView.Adapter<TrendingProductsAdapter.TrendingProductsAdapterViewHolder>() {
    class TrendingProductsAdapterViewHolder(item: View):RecyclerView.ViewHolder(item) {

        val trendingImageView=itemView.findViewById<ImageView>(R.id.secondListImageView)
        val trendingTittleTextView=itemView.findViewById<TextView>(R.id.secondListTittleTV)
        val trendingDescriptionTextView=itemView.findViewById<TextView>(R.id.secondListTittleTV2)
        val trendingDescription2TextView=itemView.findViewById<TextView>(R.id.secondListTittleTV3)
        val trendingPriceTextView=itemView.findViewById<TextView>(R.id.secondListPriceTV)

    }

    @SuppressLint("NotifyDataSetChanged")
    fun filteredList(womenProductsFilteredList: ArrayList<womenProductsDataModel>){
        trendingProductsList = womenProductsFilteredList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TrendingProductsAdapterViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.second_horizontal_layout,parent,false)
        return TrendingProductsAdapterViewHolder(view)
    }

    override fun getItemCount(): Int {
        return trendingProductsList.size

    }

    override fun onBindViewHolder(holder: TrendingProductsAdapterViewHolder, position: Int) {
        val trendingProducts=trendingProductsList[position]

        Glide.with(holder.itemView.context).load(trendingProducts.womenImage).into(holder.trendingImageView)
        holder.trendingTittleTextView.text=trendingProducts.womenTittle
        holder.trendingDescriptionTextView.text=trendingProducts.womenDescription
        holder.trendingDescription2TextView.text=trendingProducts.womenDescription2
        holder.trendingPriceTextView.text=trendingProducts.womenProductPrice
        holder.itemView.setOnClickListener {
            productDetailsOnclick.productsDetailsOnclick(trendingProducts)
        }

    }
}