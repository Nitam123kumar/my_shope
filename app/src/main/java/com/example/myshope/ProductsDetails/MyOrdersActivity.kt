package com.example.myshope.ProductsDetails

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myshope.AllAdapter.MyOrderAdapter
import com.example.myshope.AllDataModel.MyOrderDataModel
import com.example.myshope.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MyOrdersActivity : AppCompatActivity() {
    lateinit var myOrdersRecyclerView: RecyclerView
    lateinit var myOrderAdapter: MyOrderAdapter
    var myOrderList = ArrayList<MyOrderDataModel>()
    lateinit var db: FirebaseDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_my_orders)

        db = FirebaseDatabase.getInstance()


        myOrdersRecyclerView = findViewById(R.id.myOrdersRecyclerView)
        myOrdersRecyclerView.layoutManager = LinearLayoutManager(this)
        myOrderAdapter = MyOrderAdapter(myOrderList)
        myOrdersRecyclerView.adapter = myOrderAdapter
        getMyOrderList()

    }

    private fun getMyOrderList() {
        db.getReference("users").child("myOrder")
            .child(FirebaseAuth.getInstance().currentUser?.uid!!)
            .addValueEventListener(object : ValueEventListener {
                @SuppressLint("NotifyDataSetChanged")
                override fun onDataChange(snapshot: DataSnapshot) {
                    myOrderList.clear()
                    for (dataSnapshot in snapshot.children) {
                        val myOrderDataModel = dataSnapshot.getValue(MyOrderDataModel::class.java)
                        myOrderList.add(myOrderDataModel!!)

                    }
                    myOrderAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
    }
}

