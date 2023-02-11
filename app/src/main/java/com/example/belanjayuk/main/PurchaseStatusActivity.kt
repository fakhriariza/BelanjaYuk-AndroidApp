package com.example.belanjayuk.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.belanjayuk.databinding.ActivityPurchaseStatusBinding
import com.example.belanjayuk.landing.LoginActivity
import com.example.belanjayuk.localmanager.LocalConfirmModel
import com.example.belanjayuk.localmanager.PrefManager

class PurchaseStatusActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPurchaseStatusBinding
    private lateinit var adapter: ConfirmDetailAdapter
    private lateinit var prefManager: PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPurchaseStatusBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefManager = PrefManager(this)
        val data: ArrayList<LocalConfirmModel> = prefManager.loadConfirm()
        setAdapter(data)
        initView()
    }

    private fun initView() = with(binding) {
        if (intent.hasExtra(ProductDetailActivity.TOTAL_PRICE)) {
            tvPrice.text = prefManager.currencyChanger(intent.getIntExtra(ProductDetailActivity.TOTAL_PRICE, 0))
        }
        btnBack.setOnClickListener{
            prefManager.clearProductDetailData()
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }
        tvSignOut.setOnClickListener {
            prefManager.removeData()
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setAdapter(data: ArrayList<LocalConfirmModel>) {
        adapter = ConfirmDetailAdapter(data)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvProductDetail.layoutManager = layoutManager
        binding.rvProductDetail.adapter = adapter.apply {

        }
    }
}