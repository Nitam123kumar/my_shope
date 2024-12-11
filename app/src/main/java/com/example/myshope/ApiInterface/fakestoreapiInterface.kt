package com.example.myshope.ApiInterface

import android.telecom.Call
import com.example.myshope.AllDataModel.FakeStoreApiDataModel
import retrofit2.http.GET

interface fakestoreapiInterface {
    @GET("products")
    fun getProducts(): retrofit2.Call<List<FakeStoreApiDataModel>>
}