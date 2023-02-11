package com.example.belanjayuk.localmanager

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PrefManager(var context: Context?) {
    val PRIVATE_MODE = 0
    private val PREF_NAME = "SharedPreferences"
    private val IS_LOGIN = "is_login"
    var localArray: ArrayList<LocalModel> = ArrayList()
    var localConfirmArray: ArrayList<LocalConfirmModel> = ArrayList()
    var isDataEmpty: Boolean = true

    var pref: SharedPreferences? = context?.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
    var editor: SharedPreferences.Editor? = pref?.edit()

    fun setLogin(isLogin: Boolean) {
        editor?.putBoolean(IS_LOGIN, isLogin)
        editor?.commit()
    }

    fun setUsername(username: String?) {
        editor?.putString("username", username)
        editor?.commit()
    }

    fun isLogin(): Boolean? {
        return pref?.getBoolean(IS_LOGIN, false)
    }

    fun getUsername(): String? {
        return pref?.getString("username", "")
    }

    fun addToCarts(titleProduct: String?, price: Int?, image: String?, quantity: Int) {
        val gson = Gson()
        localArray.add(LocalModel(titleProduct, price, image, quantity))
        val json = gson.toJson(localArray)
        editor?.putString("productDetail", json)
        editor?.apply()
    }

    fun loadCarts(): ArrayList<LocalModel> {
        val gson = Gson()
        val json = pref?.getString("productDetail", null)
        val type = object : TypeToken<ArrayList<LocalModel?>?>() {}.type

        if (json != null) {
            localArray = gson.fromJson(json, type)
            isDataEmpty = true
        } else {
            localArray = ArrayList()
            isDataEmpty = false
        }
        return localArray
    }
    fun addConfirm(titleProduct: String?, price: Int?, quantity: Int, totalPrice: Int) {
        val gson = Gson()
        localConfirmArray.add(LocalConfirmModel(titleProduct, price, quantity, totalPrice))
        val json = gson.toJson(localConfirmArray)
        editor?.putString("confirmDetail", json)
        editor?.apply()
    }

    fun loadConfirm(): ArrayList<LocalConfirmModel> {
        val gson = Gson()
        val json = pref?.getString("confirmDetail", null)
        val type = object : TypeToken<ArrayList<LocalConfirmModel?>?>() {}.type

        if (json != null) {
            localConfirmArray = gson.fromJson(json, type)
            isDataEmpty = true
        } else {
            localConfirmArray = ArrayList()
            isDataEmpty = false
        }
        return localConfirmArray
    }

    fun clearProductDetailData () {
        editor?.remove("productDetail")?.commit()
    }

    fun currencyChanger(price: Int?): String {
        return "Rp $price"
    }


    fun removeData() {
        editor?.clear()
        editor?.commit()
    }
}