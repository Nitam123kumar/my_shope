package com.example.myshope.AllFragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myshope.AllAdapter.CartAdapter
import com.example.myshope.AllDataModel.AddCartDataModel
import com.example.myshope.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

@Suppress("DEPRECATION")
class CardFragment : Fragment() {
    lateinit var db: FirebaseDatabase
    var cartList = ArrayList<AddCartDataModel>()
    lateinit var cartAdapter: CartAdapter
    private lateinit var cartRecyclerView: RecyclerView
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_card, container, false)
        db = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()
        cartRecyclerView = view.findViewById(R.id.cardRecyclerView)
        cartRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        cartAdapter = CartAdapter(cartList)
        cartRecyclerView.adapter = cartAdapter

        getCartData()



        return view
    }

    private fun getCartData() {

        val progressDialog = android.app.ProgressDialog(requireContext())
        progressDialog.setMessage("Getting Data Add  To Cart...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        db.getReference("addToCart").child(auth.currentUser?.uid!!)
            .addValueEventListener(object : ValueEventListener {
                @SuppressLint("NotifyDataSetChanged")
                override fun onDataChange(snapshot: DataSnapshot) {
                    cartList.clear()
                    for (snap in snapshot.children) {
                        val data = snap.getValue(AddCartDataModel::class.java)
                        cartList.add(data!!)


                    }
                    cartAdapter.notifyDataSetChanged()
                    progressDialog.dismiss()
//                    Toast.makeText(requireActivity(), "Success", Toast.LENGTH_SHORT).show()
                }

                override fun onCancelled(error: DatabaseError) {
                    progressDialog.dismiss()
                    Toast.makeText(requireContext(), "Failed", Toast.LENGTH_SHORT).show()
                }
            })
    }

}