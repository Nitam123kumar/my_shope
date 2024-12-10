package com.example.myshope.ProductsDetails

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.myshope.R

class ProductDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_product_detail)

        val products = intent.getStringExtra("image")
        val name = intent.getStringExtra("name")
        val price = intent.getStringExtra("price")

       val productImage=findViewById<ImageView>(R.id.productImage)
       val productName=findViewById<TextView>(R.id.productName)
        val discountPrice= findViewById<TextView>(R.id.discountPrice)

        Glide.with(this).load(products).placeholder(R.drawable.imarhs).into(productImage)
        productName.text=name
        discountPrice.text=price

    }
}