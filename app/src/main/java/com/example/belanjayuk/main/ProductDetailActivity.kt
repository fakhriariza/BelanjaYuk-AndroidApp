package com.example.belanjayuk.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.belanjayuk.databinding.ActivityProductDetailBinding
import com.example.belanjayuk.localmanager.PrefManager

class ProductDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailBinding
    private lateinit var prefManager: PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefManager = PrefManager(this)
        initView()
    }

    private fun initView() = with(binding) {
        if (intent.hasExtra(PRODUCT_TILE))
            tvProductTitle.text = intent.getStringExtra(PRODUCT_TILE)
        if (intent.hasExtra(PRICE))
            tvPrice.text = prefManager.currencyChanger(intent.getIntExtra(PRICE, 0))
        if (intent.hasExtra(DIMENSION))
            tvDimension.text = intent.getStringExtra(DIMENSION)
        if (intent.hasExtra(UNIT))
            tvUnit.text = intent.getStringExtra(UNIT)
        if (intent.hasExtra(IMAGE))
            ivProduct.let {
                Glide.with(it)
                    .load(intent.getStringExtra(IMAGE))
                    .into(it)
            }
        ivBack.setOnClickListener {
            onBackPressed()
        }

    }

    companion object {
        const val PRODUCT_TILE = "productTitle"
        const val PRICE = "price"
        const val DIMENSION = "dimension"
        const val UNIT = "unit"
        const val IMAGE = "image"
        const val TOTAL_PRICE = "totalPrice"
    }
}