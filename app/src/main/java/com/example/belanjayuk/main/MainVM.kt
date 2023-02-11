package com.example.belanjayuk.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.belanjayuk.networking.BelanjaYukApiService
import com.example.belanjayuk.networking.BelanjaYukMockApi
import retrofit2.Call
import retrofit2.Response

class MainVM: ViewModel() {
    val productDetailResponse = MutableLiveData<ProductDetailData>()
    val retrofit = BelanjaYukMockApi.getInstance(BelanjaYukApiService::class.java)

    fun fetchProductDetail() {
        val apiCall = retrofit.getProductDetail()
        apiCall?.enqueue(object : retrofit2.Callback<ProductDetailData?> {
            override fun onResponse(
                call: Call<ProductDetailData?>,
                response: Response<ProductDetailData?>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        productDetailResponse.value = it
                    }
                }
            }
            override fun onFailure(call: Call<ProductDetailData?>, t: Throwable) {
            }
        })
    }
}