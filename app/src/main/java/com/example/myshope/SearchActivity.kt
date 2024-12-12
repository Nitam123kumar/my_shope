package com.example.myshope

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myshope.AllAdapter.ApiAdapter
import com.example.myshope.AllAdapter.StatusAdapter
import com.example.myshope.AllAdapter.TrendingProductsAdapter
import com.example.myshope.AllAdapter.WomenProductsAdapter
import com.example.myshope.AllDataModel.FakeStoreApiDataModel
import com.example.myshope.AllDataModel.StatusDataModel
import com.example.myshope.AllDataModel.womenProductsDataModel

class SearchActivity : AppCompatActivity() {
    lateinit var statusRecyclerViewSearchActivity: RecyclerView
    lateinit var SecondRecyclerViewSearchActivity: RecyclerView
    lateinit var ThirdRecyclerViewSearchActivity: RecyclerView
    lateinit var statusAdapter: StatusAdapter
    lateinit var secondAdapter: TrendingProductsAdapter
    lateinit var thirdAdapter: ApiAdapter
    lateinit var statusList: ArrayList<StatusDataModel>
    lateinit var secondList: ArrayList<womenProductsDataModel>
    lateinit var thirdList: ArrayList<FakeStoreApiDataModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_search)

        statusRecyclerViewSearchActivity=findViewById(R.id.statusRecyclerViewSearchActivity)
        SecondRecyclerViewSearchActivity=findViewById(R.id.SecondRecyclerViewSearchActivity)
        ThirdRecyclerViewSearchActivity=findViewById(R.id.ThirdRecyclerViewSearchActivity)

        statusRecyclerViewSearchActivity.layoutManager= GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false)
        SecondRecyclerViewSearchActivity.layoutManager= GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false)
        ThirdRecyclerViewSearchActivity.layoutManager= GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false)
        statusAdapter= StatusAdapter(statusList)
        secondAdapter= TrendingProductsAdapter(secondList)
        thirdAdapter= ApiAdapter(thirdList)

        statusRecyclerViewSearchActivity.adapter= statusAdapter
        SecondRecyclerViewSearchActivity.adapter= secondAdapter
        ThirdRecyclerViewSearchActivity.adapter= thirdAdapter


    }
}