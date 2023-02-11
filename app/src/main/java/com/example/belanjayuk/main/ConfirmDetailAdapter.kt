package com.example.belanjayuk.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.belanjayuk.databinding.ItemResultBinding
import com.example.belanjayuk.localmanager.LocalConfirmModel
import com.example.belanjayuk.localmanager.PrefManager

class ConfirmDetailAdapter(
    private val productList: List<LocalConfirmModel>
    ) : RecyclerView.Adapter<ConfirmDetailAdapter.ListViewHolder>() {
    private lateinit var prefManager: PrefManager

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, i: Int) {
        prefManager = PrefManager(holder.itemView.context)

        holder.binding.apply {
            val productName = productList[i].productTitle
            val price = productList[i].price
            val quantity = productList[i].quantity
            val totalPrice = productList[i].totalPrice

            tvProductTitle.text = productName
            tvQuantity.text = quantity.toString()
            tvPrice.text = prefManager.currencyChanger(price)
            tvTotalPrice.text = prefManager.currencyChanger(totalPrice)
        }
    }

    override fun getItemCount(): Int = productList.size

    class ListViewHolder(val binding: ItemResultBinding) : RecyclerView.ViewHolder(binding.root) {}
}