package com.example.myshope

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.razorpay.Checkout
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener
import org.json.JSONException
import org.json.JSONObject

class RazorPayActivity : AppCompatActivity(),PaymentResultWithDataListener {
    lateinit var sizeSpinner: Spinner
    lateinit var qtySpinner: Spinner
    lateinit var productTittleTV: TextView
    lateinit var productDesTV: TextView
    lateinit var priceTV: TextView
    lateinit var cartImageView: ImageView
    lateinit var backOn: ImageView
    lateinit var priceTV1: TextView
    lateinit var priceTV2: TextView
    lateinit var proceedBtn: AppCompatButton
    var hasMap=HashMap<String,Any>()
    lateinit var db: FirebaseDatabase
    var id:String?=null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_razor_pay)

        db=FirebaseDatabase.getInstance()

        sizeSpinner = findViewById(R.id.sizeSpinner)
        qtySpinner = findViewById(R.id.qtySpinner)
        productTittleTV = findViewById(R.id.productTittleTV)
        productDesTV = findViewById(R.id.productDesTV)
        priceTV = findViewById(R.id.priceTextView)
        cartImageView = findViewById(R.id.cartImageView)
        backOn = findViewById(R.id.backOn)
        priceTV1 = findViewById(R.id.priceTextView1)
        priceTV2 = findViewById(R.id.priceTextView2)
        proceedBtn = findViewById(R.id.proceedBtn)

        val productImage = intent.getStringExtra("image")
        val price = intent.getStringExtra("price")
        val title = intent.getStringExtra("title")
        val description = intent.getStringExtra("description")
        id = intent.getStringExtra("id")

        hasMap["orderId"] = id.toString()
        hasMap["orderTittle"] = title.toString()
        hasMap["orderDescription"] = description.toString()
        hasMap["orderProductPrice"] = price.toString()
        hasMap["orderImage"] = productImage.toString()



        Glide.with(this).load(productImage).placeholder(R.drawable.imarhs).into(cartImageView)
        productTittleTV.text = title
        productDesTV.text = description
        priceTV.text = price
        priceTV1.text = price
        priceTV2.text = price

        backOn.setOnClickListener {
            finish()
        }

       proceedBtn.setOnClickListener {
           startPayment()
       }


    }

    private fun startPayment() {
        val activity: Activity = this
        val co = Checkout()
        co.setKeyID("rzp_test_7QRDUFvP75uLJS")
        val amount = (priceTV2.text.toString().replace("₹", "").toDouble() * 100).toInt()



        try {
            val options = JSONObject()
            options.put("name", "Razorpay Corp")
            options.put("description", "Demoing Charges")
            options.put("image", "http://example.com/image/rzp.jpg")
            options.put("theme.color", "#3399cc")
            options.put("currency", "INR")
            options.put("amount", amount)

            val retryObj = JSONObject()
            retryObj.put("enabled", true)
            retryObj.put("max_count", 4)
            options.put("retry", retryObj)
            val prefill = JSONObject()
            prefill.put("email", "gaurav.kumar@example.com")
            prefill.put("contact", "9876543210")

            options.put("prefill", prefill)
            co.open(activity, options)
        } catch (e: JSONException) {
            Toast.makeText(activity, "Error in payment: " + e.message, Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }


    override fun onPaymentSuccess(p0: String?, p1: PaymentData?) {
        Toast.makeText(this, "Payment Successful", Toast.LENGTH_SHORT).show()
        val uuid=FirebaseAuth.getInstance().currentUser?.uid
        db.getReference("users").child("myOrder").child(uuid!!).child(id!!).setValue(hasMap)
            .addOnSuccessListener {
                Toast.makeText(this, "myOrder Successful", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "myOrder Failed", Toast.LENGTH_SHORT).show()
            }

    }

    override fun onPaymentError(p0: Int, p1: String?, p2: PaymentData?) {
        Toast.makeText(this, "Payment Failed", Toast.LENGTH_SHORT).show()
    }

}