package com.example.belanjayuk.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.belanjayuk.R
import com.example.belanjayuk.databinding.ItemCheckoutBinding
import com.example.belanjayuk.localmanager.LocalModel
import com.example.belanjayuk.localmanager.PrefManager

class CheckOutCardAdapter(
    private val productList: ArrayList<LocalModel>
    ) : RecyclerView.Adapter<CheckOutCardAdapter.ListViewHolder>() {
    private lateinit var prefManager: PrefManager
    var mListener: Listener? = null
    private var quantitys: Int = 1
    private var totalPrice: Int? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemCheckoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ListViewHolder, i: Int) {
        prefManager = PrefManager(holder.itemView.context)

        holder.binding.apply {
            var clicked = 0
            val productName = productList[i].productTitle
            val price = productList[i].price
            val image = productList[i].image
            var quantity = productList[i].quantity

            tvProductTitle.text = productName
            tvPrice.text = prefManager.currencyChanger(price)
            ivProduct.let {
                Glide.with(it)
                    .load(image)
                    .into(it)
            }
            tvTotalQuantity.text = quantitys.toString()
            ivPlus.setOnClickListener{
                quantity++
                tvTotalQuantity.text = quantity.toString()
                if (price != null) {
                    tvPrice.text = prefManager.currencyChanger(price.times(quantity))
                    quantitys = quantity
                }
            }
            ivMinus.setOnClickListener{
                quantity--
                tvTotalQuantity.text = quantity.toString()
                if (price != null) {
                    tvPrice.text = prefManager.currencyChanger(price.times(quantity))
                    quantitys = quantity
                }
            }
            btnConfirm.setOnClickListener {
                clicked++
                if (price != null) {
                    totalPrice = price.times(quantitys)
                }
                totalPrice?.let { it1 ->
                    prefManager.addConfirm(productName, price, quantitys,
                        it1
                    )
                }
                if (price != null) {
                    mListener?.onClick(price, quantitys)
                }
                if (clicked == 1) {
                    btnConfirm.background = ResourcesCompat.getDrawable(holder.itemView.resources, R.drawable.secondary_button, null)
                    btnConfirm.isEnabled = false
                }
            }
        }
    }

    override fun getItemCount(): Int = productList.size

    class ListViewHolder(val binding: ItemCheckoutBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    interface Listener {
        fun onClick(price: Int, quantity: Int)
    }
}