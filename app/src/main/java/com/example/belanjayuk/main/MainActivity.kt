package com.example.belanjayuk.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.belanjayuk.R
import com.example.belanjayuk.databinding.ActivityMainBinding
import com.example.belanjayuk.localmanager.PrefManager

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var prefManager: PrefManager
    private lateinit var viewModel: MainVM
    private var total: Int = 0

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefManager = PrefManager(this)
        viewModel = ViewModelProvider(this)[MainVM::class.java]
        initView()
    }

    @SuppressLint("SetTextI18n")
    private fun initView() = with(binding) {
        val username = prefManager.getUsername()
        tvTitle.text = "Halo $username! Pilih kebutuhanmu disini ya!"

        rlCheckOut.setOnClickListener {
            rlCheckOut.isEnabled = false
        }
        rlCheckOut.background = ResourcesCompat.getDrawable(resources, R.drawable.secondary_button, null)
        setAdapter()
    }

    private fun setAdapter() {
        viewModel.fetchProductDetail()
        viewModel.productDetailResponse.observe(this) {response: ProductDetailData ->
            val data = response.data
            val layoutManager = GridLayoutManager(this, 2)
            binding.rvProductList.layoutManager = layoutManager
            binding.rvProductList.adapter = ProductDetailAdapter(data).apply {
                mListener = object : ProductDetailAdapter.Listener {
                    override fun onClick(int: Int) {
                        setTotalPrice(int)
                        binding.rlCheckOut.isEnabled = true
                        binding.rlCheckOut.background = ResourcesCompat.getDrawable(resources, R.drawable.primary_button, null)
                        binding.rlCheckOut.setOnClickListener {
                            val intent = Intent(applicationContext, CheckOutActivity::class.java)
                            startActivity(intent)
                        }
                    }
                }
            }
        }
    }

    private fun setTotalPrice(price : Int) = with(binding) {
        total += (price)
        tvTotalPrice.text = prefManager.currencyChanger(total)
    }
}