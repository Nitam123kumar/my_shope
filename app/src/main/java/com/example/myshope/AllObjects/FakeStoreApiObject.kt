package com.example.myshope.AllObjects

import com.example.myshope.ApiInterface.fakestoreapiInterface
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object FakeStoreApiObject {

    val fakeStoreApi:fakestoreapiInterface by lazy {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://fakestoreapi.com/")
            .build()
            .create(fakestoreapiInterface::class.java)
    }

}