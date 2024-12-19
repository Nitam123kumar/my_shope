package com.example.myshope

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.SearchView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myshope.AllAdapter.ApiAdapter
import com.example.myshope.AllAdapter.StatusAdapter
import com.example.myshope.AllAdapter.TrendingProductsAdapter
import com.example.myshope.AllDataModel.FakeStoreApiDataModel
import com.example.myshope.AllDataModel.StatusDataModel
import com.example.myshope.AllDataModel.womenProductsDataModel
import com.example.myshope.AllObjects.FakeStoreApiObject
import com.example.myshope.OnClickInterface.ApiProductsDetailsOnclick
import com.example.myshope.OnClickInterface.ProductsDetailsOnclick
import com.example.myshope.ProductsDetails.ProductDetailActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Locale

class SearchActivity : AppCompatActivity() ,ApiProductsDetailsOnclick  ,ProductsDetailsOnclick{
    private lateinit var statusRecyclerViewSearchActivity: RecyclerView
    private lateinit var SecondRecyclerViewSearchActivity: RecyclerView
    private lateinit var ThirdRecyclerViewSearchActivity: RecyclerView
    lateinit var statusAdapter: StatusAdapter
    lateinit var secondAdapter: TrendingProductsAdapter
    lateinit var thirdAdapter: ApiAdapter
    lateinit var statusList: ArrayList<StatusDataModel>
    lateinit var secondList: ArrayList<womenProductsDataModel>
    lateinit var thirdList: ArrayList<FakeStoreApiDataModel>
    lateinit var db: FirebaseDatabase
    private lateinit var searchView: SearchView
    var filteredList=ArrayList<womenProductsDataModel>()
    var filteredList1=ArrayList<StatusDataModel>()
    var filteredList2=ArrayList<FakeStoreApiDataModel>()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_search)

        db = FirebaseDatabase.getInstance()
        statusList = ArrayList()
        secondList = ArrayList()
        thirdList = ArrayList()

        val onBack=findViewById<ImageView>(R.id.backActivity)

        onBack.setOnClickListener {
            finish()
        }

        statusRecyclerViewSearchActivity=findViewById(R.id.statusRecyclerViewSearchActivity)
        SecondRecyclerViewSearchActivity=findViewById(R.id.SecondRecyclerViewSearchActivity)
        ThirdRecyclerViewSearchActivity=findViewById(R.id.ThirdRecyclerViewSearchActivity)
        searchView=findViewById(R.id.dataSearchView)

        statusRecyclerViewSearchActivity.layoutManager= LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        SecondRecyclerViewSearchActivity.layoutManager=  LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        ThirdRecyclerViewSearchActivity.layoutManager= GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false)
        statusAdapter= StatusAdapter(statusList)
        secondAdapter= TrendingProductsAdapter(secondList,this)
        thirdAdapter= ApiAdapter(thirdList,this)

        statusRecyclerViewSearchActivity.adapter= statusAdapter
        SecondRecyclerViewSearchActivity.adapter= secondAdapter
        ThirdRecyclerViewSearchActivity.adapter= thirdAdapter
        statusGetData()
        womenProductsGetData()
        getApiData()

                searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            @SuppressLint("NotifyDataSetChanged", "DefaultLocale")
            override fun onQueryTextChange(newText: String?): Boolean {
                filteredList.clear()

                if (newText != null) {
                    secondList.forEach {
                        if (it.womenTittle?.lowercase()?.contains(newText.lowercase(Locale.getDefault())) == true) {
                            filteredList.add(it)
                        }
                    }
                    filteredList1.clear()
                    statusList.forEach {
                        if (it.statusTittle?.lowercase()?.contains(newText.lowercase(Locale.getDefault())) == true) {
                            filteredList1.add(it)
                        }
                    }
                    filteredList2.clear()
                    thirdList.forEach {
                        if (it.title?.lowercase()?.contains(newText.lowercase(Locale.getDefault())) == true) {
                            filteredList2.add(it)
                        }
                    }
                }
                secondAdapter.filteredList(filteredList)
                statusAdapter.filteredList(filteredList1)
                thirdAdapter.filteredList(filteredList2)
                secondAdapter.notifyDataSetChanged()
                return true
            }
        })


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
                secondList.clear()
                for (snap in snapshot.children) {
                    val womenProducts = snap.getValue(womenProductsDataModel::class.java)
                    secondList.add(womenProducts!!)
                }
                secondAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    fun getApiData(){
        FakeStoreApiObject.fakeStoreApi.getProducts().enqueue(object :
            Callback<List<FakeStoreApiDataModel>> {
            @SuppressLint("SuspiciousIndentation", "NotifyDataSetChanged")
            override fun onResponse(
                call: Call<List<FakeStoreApiDataModel>>,
                response: Response<List<FakeStoreApiDataModel>>
            ) {
                if (response.isSuccessful){
                    val  data= response.body() as ArrayList<FakeStoreApiDataModel>
                    thirdList.addAll(data)
                }
                thirdAdapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<List<FakeStoreApiDataModel>>, t: Throwable) {
            }

        })
    }

    override fun apiProductsDetailsOnclick(dataModel: FakeStoreApiDataModel) {

        val intent= Intent(this,ProductDetailActivity::class.java)

        intent.putExtra("image",dataModel.image)
        intent.putExtra("name",dataModel.title)
        intent.putExtra("price",dataModel.price)
        intent.putExtra("description",dataModel.description)
        intent.putExtra("title",dataModel.title)
        intent.putExtra("id",dataModel.id)
        startActivity(intent)

    }

    override fun productsDetailsOnclick(dataModel: womenProductsDataModel) {
        val intent= Intent(Intent(this, ProductDetailActivity::class.java))
        intent.putExtra("image",dataModel.womenImage)
        intent.putExtra("name",dataModel.womenTittle)
        intent.putExtra("price",dataModel.womenProductPrice)
        intent.putExtra("description",dataModel.womenDescription)
        intent.putExtra("title",dataModel.womenTittle)
        intent.putExtra("id",dataModel.womenId)
        startActivity(intent)

    }


}