package com.example.belanjayuk.main

import android.annotation.SuppressLint
import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
@Keep
data class ProductDetailData(
    @SerializedName("data")
    val data: List<ProductDetail> = arrayListOf()
) : Parcelable {
    @SuppressLint("ParcelCreator")
    @Parcelize
    @Keep
    data class ProductDetail(
        @SerializedName("productCode")
        val productCode: String? = "",
        @SerializedName("productName")
        val productName: String? = "",
        @SerializedName("price")
        val price: Int,
        @SerializedName("currency")
        val currency: String? = "",
        @SerializedName("discount")
        val discount: Int? = null,
        @SerializedName("dimension")
        val dimension: String? = "",
        @SerializedName("unit")
        val unit: String? = "",
        @SerializedName("image")
        val image: String? = ""
    ) : Parcelable
}
