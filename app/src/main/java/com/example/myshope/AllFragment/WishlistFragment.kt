package com.example.myshope.AllFragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myshope.AllAdapter.WomenProductsAdapter
import com.example.myshope.AllDataModel.womenProductsDataModel
import com.example.myshope.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class WishlistFragment : Fragment() {
        lateinit var allDataRecyclerView: RecyclerView
    lateinit var womenProductsAdapter: WomenProductsAdapter
    var womenProductsList = ArrayList<womenProductsDataModel>()
    lateinit var db: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_wishlist, container, false)

        allDataRecyclerView=view.findViewById(R.id.all_Data_RecyclerView)

        allDataRecyclerView.layoutManager=GridLayoutManager(requireContext(),2,GridLayoutManager.VERTICAL,false)
        womenProductsAdapter= WomenProductsAdapter(womenProductsList)
        allDataRecyclerView.adapter=womenProductsAdapter
        db= FirebaseDatabase.getInstance()
        womenProductsGetData()

        return view
    }

   private fun womenProductsGetData() {
        db.getReference("users").child("womenProducts").addValueEventListener(object :
            ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                womenProductsList.clear()
                for (snap in snapshot.children) {
                    val womenProducts = snap.getValue(womenProductsDataModel::class.java)
                    womenProductsList.add(womenProducts!!)
                }
                womenProductsAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

}