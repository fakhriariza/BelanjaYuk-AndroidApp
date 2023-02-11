package com.example.belanjayuk.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.belanjayuk.R
import com.example.belanjayuk.localmanager.LocalModel
import com.example.belanjayuk.localmanager.PrefManager
import com.example.belanjayuk.databinding.ActivityCheckOutBinding

class CheckOutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCheckOutBinding
    private lateinit var prefManager: PrefManager
    private lateinit var adapter: CheckOutCardAdapter
    private var total: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckOutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefManager = PrefManager(this)
        initView()
    }

    private fun initView() = with(binding) {
        val data: ArrayList<LocalModel> = prefManager.loadCarts()

        ivBack.setOnClickListener {
            onBackPressed()
        }
        rlPay.background = ResourcesCompat.getDrawable(resources, R.drawable.secondary_button, null)
        rlPay.setOnClickListener{
            rlPay.isEnabled = false
        }
        setAdapter(data)
    }

    private fun setAdapter(data: ArrayList<LocalModel>) {
        adapter = CheckOutCardAdapter(data)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvProductList.layoutManager = layoutManager
        binding.rvProductList.adapter = adapter.apply {
            mListener = object : CheckOutCardAdapter.Listener {
                override fun onClick(price: Int, quantity: Int) {
                    setTotalPrice(price, quantity)
                    binding.rlPay.isEnabled = true
                    binding.rlPay.background = ResourcesCompat.getDrawable(resources, R.drawable.primary_button, null)
                    binding.rlPay.setOnClickListener {
                        val intent = Intent(applicationContext, PurchaseStatusActivity::class.java)
                        intent.putExtra(ProductDetailActivity.TOTAL_PRICE, total)
                        startActivity(intent)
                    }
                }
            }
        }
    }

    private fun setTotalPrice(price: Int, quantity: Int) = with(binding) {
        val calculate =  price * quantity
        total += calculate
        tvTotalPrice.text = prefManager.currencyChanger(total)
    }
}