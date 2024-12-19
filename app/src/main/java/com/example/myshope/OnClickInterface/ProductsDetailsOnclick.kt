package com.example.myshope.OnClickInterface

import com.example.myshope.AllDataModel.FakeStoreApiDataModel
import com.example.myshope.AllDataModel.womenProductsDataModel

interface ProductsDetailsOnclick {
    fun productsDetailsOnclick(dataModel: womenProductsDataModel)
}
interface ApiProductsDetailsOnclick{
    fun apiProductsDetailsOnclick(dataModel: FakeStoreApiDataModel)
}