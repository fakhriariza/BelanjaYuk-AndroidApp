package com.example.belanjayuk.networking

import com.example.belanjayuk.main.ProductDetailData
import retrofit2.Call
import retrofit2.http.*

interface BelanjaYukApiService {

    @GET("product/detail")
    fun getProductDetail():
            Call<ProductDetailData?>?
}