package com.example.myshope.AllFragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SearchView
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myshope.AllAdapter.ApiAdapter
import com.example.myshope.AllAdapter.WomenProductsAdapter
import com.example.myshope.AllDataModel.FakeStoreApiDataModel
import com.example.myshope.AllDataModel.womenProductsDataModel
import com.example.myshope.AllObjects.FakeStoreApiObject
import com.example.myshope.LoginActivity
import com.example.myshope.OnClickInterface.ApiProductsDetailsOnclick
import com.example.myshope.OnClickInterface.ProductsDetailsOnclick
import com.example.myshope.ProductsDetails.MyOrdersActivity
import com.example.myshope.ProductsDetails.ProductDetailActivity
import com.example.myshope.R
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Locale

class WishlistFragment : Fragment(), ProductsDetailsOnclick, ApiProductsDetailsOnclick {
    private lateinit var allDataRecyclerView: RecyclerView
    private lateinit var apiProductsRecyclerView: RecyclerView
    lateinit var apiProductsAdapter: ApiAdapter
    var apiProductsList = ArrayList<FakeStoreApiDataModel>()
    lateinit var womenProductsAdapter: WomenProductsAdapter
    var womenProductsList = ArrayList<womenProductsDataModel>()
    lateinit var db: FirebaseDatabase
    private lateinit var searchView2: SearchView
    var filterList = ArrayList<womenProductsDataModel>()
    var filteredList2 = ArrayList<FakeStoreApiDataModel>()
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_wishlist, container, false)

        allDataRecyclerView = view.findViewById(R.id.all_Data_RecyclerView)
        apiProductsRecyclerView = view.findViewById(R.id.api_Data_RecyclerView)
        apiProductsAdapter = ApiAdapter(apiProductsList, this)
        apiProductsRecyclerView.layoutManager =
            GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        apiProductsRecyclerView.adapter = apiProductsAdapter

        val drawerImageView = view.findViewById<ImageView>(R.id.drawerImageView_Wishlist)
        drawerLayout = view.findViewById(R.id.drawer_layout_Wishlist)
        val navigationView = view.findViewById<NavigationView>(R.id.navigation_Wishlist)
        val profileImageView=view.findViewById<ImageView>(R.id.profile_Wishlist)

        drawerImageView.setOnClickListener {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                // Agar drawer open hai to band karo
                drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                // Agar drawer band hai to open karo
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }

        profileImageView.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, SettingFragment())
                .addToBackStack(null)
                .commit()
        }

        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_logout -> {
                    logoutAlertDialog()
                    true

                }
                R.id.menu_wishlist -> {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.container, WishlistFragment())
                        .addToBackStack(null)
                        .commit()
                    true
                }
                R.id.menu_orders -> {
                    startActivity(Intent(requireContext(), MyOrdersActivity::class.java))
                    true
                }
                R.id.menu_profile -> {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.container, SettingFragment())
                        .addToBackStack(null)
                        .commit()
                    true
                }


                else -> {
                    false
                }
            }

        }



        allDataRecyclerView.layoutManager =
            GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        womenProductsAdapter = WomenProductsAdapter(womenProductsList, this)
        allDataRecyclerView.adapter = womenProductsAdapter
        db = FirebaseDatabase.getInstance()
        womenProductsGetData()

        searchView2 = view.findViewById(R.id.searchView2)
        searchView2.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            @SuppressLint("NotifyDataSetChanged", "DefaultLocale")
            override fun onQueryTextChange(newtext: String?): Boolean {
                filterList.clear()

                if (newtext != null) {
                    womenProductsList.forEach {
                        if (it.womenTittle?.lowercase()?.contains(newtext.lowercase(Locale.getDefault())) == true) {
                            filterList.add(it)
                        }

                    }
                    filteredList2.clear()
                    apiProductsList.forEach {
                        if (it.title?.lowercase()?.contains(newtext.lowercase(Locale.getDefault())) == true) {
                            filteredList2.add(it)
                        }
                    }
                }

                womenProductsAdapter.filteredList(filterList)
                apiProductsAdapter.filteredList(filteredList2)
                womenProductsAdapter.notifyDataSetChanged()
                apiProductsAdapter.notifyDataSetChanged()
                return true
            }

        })
        getApiData()

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

    private fun getApiData() {
        FakeStoreApiObject.fakeStoreApi.getProducts().enqueue(object :
            Callback<List<FakeStoreApiDataModel>> {
            @SuppressLint("SuspiciousIndentation", "NotifyDataSetChanged")
            override fun onResponse(
                call: Call<List<FakeStoreApiDataModel>>,
                response: Response<List<FakeStoreApiDataModel>>
            ) {
                if (response.isSuccessful) {
                    val data = response.body() as ArrayList<FakeStoreApiDataModel>
                    apiProductsList.addAll(data)
                }
                apiProductsAdapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<List<FakeStoreApiDataModel>>, t: Throwable) {
            }

        })
    }

    override fun productsDetailsOnclick(dataModel: womenProductsDataModel) {
        val intent = Intent(Intent(requireContext(), ProductDetailActivity::class.java))
        intent.putExtra("image", dataModel.womenImage)
        intent.putExtra("name", dataModel.womenTittle)
        intent.putExtra("price", dataModel.womenProductPrice)
        startActivity(intent)
    }

    override fun apiProductsDetailsOnclick(dataModel: FakeStoreApiDataModel) {
        val intent = Intent(requireContext(), ProductDetailActivity::class.java)

        intent.putExtra("image", dataModel.image)
        intent.putExtra("name", dataModel.title)
        intent.putExtra("price", dataModel.price)
        intent.putExtra("description", dataModel.description)
        intent.putExtra("title", dataModel.title)
        intent.putExtra("id", dataModel.id)
        startActivity(intent)

    }

    private fun logoutAlertDialog(){
        val dialog= AlertDialog.Builder(requireContext())
        dialog.setTitle("Logout")
        dialog.setMessage("Are you sure you want to logout?")
        dialog.setPositiveButton("Logout"){_,_->
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            activity?.finish()
        }
        dialog.setNegativeButton("Cancel"){_,_->
            dialog.create().dismiss()
        }
        dialog.create().show()


    }

}