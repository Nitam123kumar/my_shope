package com.example.myshope.AllFragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.myshope.AllAdapter.StatusAdapter
import com.example.myshope.AllAdapter.WomenProductsAdapter
import com.example.myshope.AllDataModel.StatusDataModel
import com.example.myshope.AllDataModel.womenProductsDataModel
import com.example.myshope.LoginActivity
import com.example.myshope.R
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class HomeFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var secondRecyclerView: RecyclerView
    var statusList = ArrayList<StatusDataModel>()
    lateinit var statusAdapter: StatusAdapter
    lateinit var db: FirebaseDatabase
    lateinit var womenProductsAdapter: WomenProductsAdapter
    var womenProductsList = ArrayList<womenProductsDataModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("MissingInflatedId", "CutPasteId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        db = FirebaseDatabase.getInstance()

        val drawerLayout = view.findViewById<DrawerLayout>(R.id.drawer_layout)
        val navigationView = view.findViewById<NavigationView>(R.id.navigation)
        val drawerImageView=view.findViewById<ImageView>(R.id.drawerImageView)

        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_logout -> {
                    FirebaseAuth.getInstance().signOut()
                    startActivity(Intent(requireContext(), LoginActivity::class.java))
                    drawerLayout.closeDrawer(GravityCompat.START)
                    activity?.finish()
                    true

                }

                else -> {
                    false
                }
            }

        }


        drawerImageView.setOnClickListener {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                // Agar drawer open hai to band karo
                drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                // Agar drawer band hai to open karo
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }


        recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        statusAdapter = StatusAdapter(statusList)
        recyclerView.adapter = statusAdapter
        statusGetData()

        val imageSlider = view.findViewById<ImageSlider>(R.id.image_slider)

        val imageList = ArrayList<SlideModel>()

        imageList.add(SlideModel(R.drawable.slider, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.splash3, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.fashion, ScaleTypes.FIT))
        imageSlider.setImageList(imageList)

        secondRecyclerView = view.findViewById<RecyclerView>(R.id.secondRecyclerView)
        secondRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        womenProductsAdapter = WomenProductsAdapter(womenProductsList)
        secondRecyclerView.adapter = womenProductsAdapter
        womenProductsGetData()

        return view
    }

    private fun statusGetData() {

        db.getReference("users").child("status").addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                statusList.clear()
                for (snap in snapshot.children) {
                    val status = snap.getValue(StatusDataModel::class.java)
                    statusList.add(status!!)
                }
                statusAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
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