package com.example.myshope.AllAdapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myshope.AllDataModel.FakeStoreApiDataModel
import com.example.myshope.OnClickInterface.ApiProductsDetailsOnclick
import com.example.myshope.R

class ApiAdapter(var apiList:List<FakeStoreApiDataModel>,var apiProductsDetailsOnclick: ApiProductsDetailsOnclick): RecyclerView.Adapter<ApiAdapter.ApiViewHolder>() {
    class ApiViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)  {
        val image=itemView.findViewById<ImageView>(R.id.apiImageView)
        val tittle=itemView.findViewById<TextView>(R.id.apiTittleTV)
        val description=itemView.findViewById<TextView>(R.id.apiDescriptionTV2)
        val price=itemView.findViewById<TextView>(R.id.apiPriceTV)


    }

    @SuppressLint("NotifyDataSetChanged")
    fun filteredList(womenProductsFilteredList: ArrayList<FakeStoreApiDataModel>){
        apiList = womenProductsFilteredList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApiViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.api_items_layout,parent,false)
        return ApiViewHolder(view)
    }

    override fun getItemCount(): Int {
       return apiList.size
    }

    override fun onBindViewHolder(holder: ApiViewHolder, position: Int) {
        val api=apiList[position]
        holder.tittle.text=api.title
        holder.description.text=api.description
        holder.price.text=api.price.toString()

        Glide.with(holder.itemView.context).load(api.image).into(holder.image)
        holder.itemView.setOnClickListener {
            apiProductsDetailsOnclick.apiProductsDetailsOnclick(api)
        }
    }
}