package com.example.belanjayuk.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.belanjayuk.databinding.ItemProductBinding
import com.bumptech.glide.Glide
import com.example.belanjayuk.R
import com.example.belanjayuk.localmanager.PrefManager

class ProductDetailAdapter(
    private val productList: List<ProductDetailData.ProductDetail>
    ) : RecyclerView.Adapter<ProductDetailAdapter.ListViewHolder>() {
    private lateinit var prefManager: PrefManager
    var mListener: Listener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, i: Int) {
        var clicked = 0
        prefManager = PrefManager(holder.itemView.context)

        holder.binding.apply {
            val productName = productList[i].productName
            val price = productList[i].price
            val image = productList[i].image

            tvProductTitle.text = productName
            tvPrice.text = prefManager.currencyChanger(price)
            ivProduct.let {
                Glide.with(it)
                    .load(image)
                    .into(it)
            }
            btnBuy.setOnClickListener {
                clicked++
                prefManager.addToCarts(productName, price, image, 1)
                mListener?.onClick(price)
                if (clicked == 1) {
                    btnBuy.background = ResourcesCompat.getDrawable(holder.itemView.resources, R.drawable.secondary_button, null)
                    btnBuy.isEnabled = false
                }
            }
            rlProductCard.setOnClickListener {
                val intent = Intent(holder.itemView.context, ProductDetailActivity::class.java)
                intent.putExtra(ProductDetailActivity.PRODUCT_TILE, productName)
                intent.putExtra(ProductDetailActivity.PRICE, price)
                intent.putExtra(ProductDetailActivity.IMAGE, image)
                intent.putExtra(ProductDetailActivity.DIMENSION, productList[i].dimension)
                intent.putExtra(ProductDetailActivity.UNIT, productList[i].unit)
                holder.itemView.context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int = productList.size

    class ListViewHolder(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {}

    interface Listener {
        fun onClick(int: Int)
    }
}