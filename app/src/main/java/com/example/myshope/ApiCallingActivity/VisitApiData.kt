package com.example.myshope.ApiCallingActivity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myshope.AllAdapter.ApiAdapter
import com.example.myshope.AllDataModel.FakeStoreApiDataModel
import com.example.myshope.AllObjects.FakeStoreApiObject
import com.example.myshope.OnClickInterface.ApiProductsDetailsOnclick
import com.example.myshope.ProductsDetails.ProductDetailActivity
import com.example.myshope.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VisitApiData : AppCompatActivity() ,ApiProductsDetailsOnclick {
    private lateinit var searchView: SearchView
    private lateinit var apiRecyclerView: RecyclerView
    lateinit var apiAdapter: ApiAdapter
    var apiList=ArrayList<FakeStoreApiDataModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_visit_api_data)

        searchView=findViewById(R.id.searchView)
        apiRecyclerView=findViewById(R.id.apiRecyclerView)
        apiAdapter= ApiAdapter(apiList,this)
        apiRecyclerView.layoutManager= GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false)
        apiRecyclerView.adapter=apiAdapter
        getApiData()

    }

    fun getApiData(){
        FakeStoreApiObject.fakeStoreApi.getProducts().enqueue(object :Callback<List<FakeStoreApiDataModel>>{
            @SuppressLint("SuspiciousIndentation", "NotifyDataSetChanged")
            override fun onResponse(
                call: Call<List<FakeStoreApiDataModel>>,
                response: Response<List<FakeStoreApiDataModel>>
            ) {
                if (response.isSuccessful){
                  val  data= response.body() as ArrayList<FakeStoreApiDataModel>
                    apiList.addAll(data)
                }
                apiAdapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<List<FakeStoreApiDataModel>>, t: Throwable) {
            }

        })
    }

    override fun apiProductsDetailsOnclick(dataModel: FakeStoreApiDataModel) {
        val intent= Intent(this, ProductDetailActivity::class.java)

        intent.putExtra("image",dataModel.image)
        intent.putExtra("name",dataModel.title)
        intent.putExtra("price",dataModel.price)
        intent.putExtra("description",dataModel.description)
        intent.putExtra("title",dataModel.title)
        intent.putExtra("id",dataModel.id)
        startActivity(intent)
    }

}