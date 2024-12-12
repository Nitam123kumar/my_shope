package com.example.myshope.ApiCallingActivity

import android.os.Bundle
import android.widget.SearchView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myshope.AllAdapter.ApiAdapter
import com.example.myshope.AllDataModel.FakeStoreApiDataModel
import com.example.myshope.AllObjects.FakeStoreApiObject
import com.example.myshope.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VisitApiData : AppCompatActivity() {
    lateinit var searchView: SearchView
    lateinit var apiRecyclerView: RecyclerView
    lateinit var apiAdapter: ApiAdapter
    var apiList=ArrayList<FakeStoreApiDataModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_visit_api_data)

        searchView=findViewById(R.id.searchView)
        apiRecyclerView=findViewById(R.id.apiRecyclerView)
        apiAdapter= ApiAdapter(apiList)
        apiRecyclerView.layoutManager= GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false)
        apiRecyclerView.adapter=apiAdapter
        getApiData()

    }

    fun getApiData(){
        FakeStoreApiObject.fakeStoreApi.getProducts().enqueue(object :Callback<List<FakeStoreApiDataModel>>{
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

}