package com.example.myshope.ProductsDetails

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.myshope.AllFragment.CardFragment
import com.example.myshope.MainActivity
import com.example.myshope.R
import com.google.firebase.database.FirebaseDatabase
import java.util.HashMap
import java.util.UUID

class ProductDetailActivity : AppCompatActivity() {
    lateinit var addtocart:ImageView
    lateinit var buynow:ImageView
    lateinit var db:FirebaseDatabase
    var hasMap=HashMap<String,Any>()
    val id=UUID.randomUUID().toString()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_product_detail)

        db=FirebaseDatabase.getInstance()

        val products = intent.getStringExtra("image")
        val name = intent.getStringExtra("name")
        val price = intent.getStringExtra("price")
        val title=intent.getStringExtra("title")
        val description=intent.getStringExtra("description")
        val description2=intent.getStringExtra("description2")


       val productImage=findViewById<ImageView>(R.id.productImage)
       val productName=findViewById<TextView>(R.id.productName)
        val discountPrice= findViewById<TextView>(R.id.discountPrice)
        addtocart=findViewById(R.id.goToCart)
        buynow=findViewById(R.id.buyNow)

        Glide.with(this).load(products).placeholder(R.drawable.imarhs).into(productImage)
        productName.text=name
        discountPrice.text=price
        hasMap["cardId"] = id
        hasMap["cardTittle"] = title.toString()
        hasMap["cardDescription"] = description.toString()
        hasMap["cardDescription2"] = description2.toString()
        hasMap["cardProductPrice"] = price.toString()
        hasMap["cardImage"] = products.toString()

        addtocart.setOnClickListener {
            addToCartData()
        }

    }

    fun addToCartData(){


        db.getReference("addToCart").child(id).setValue(hasMap)
            .addOnSuccessListener {
                Toast.makeText(this, "Add To Cart", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("showFragment","showFragment" )
                startActivity(intent)
            }
            .addOnFailureListener {
                Toast.makeText(this, "No add To cart", Toast.LENGTH_SHORT).show()
            }
    }

}