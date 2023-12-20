package com.aditya.cryptotracker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aditya.cryptotracker.R
import com.aditya.cryptotracker.databinding.ItemCurrencyBinding
import com.aditya.cryptotracker.model.CurrencyCombinedData
import com.bumptech.glide.Glide
import javax.inject.Inject

class CurrencyAdapter @Inject constructor() :
    RecyclerView.Adapter<CurrencyAdapter.CryptoViewHolder>() {

    private var cryptoList: List<CurrencyCombinedData> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCurrencyBinding.inflate(inflater, parent, false)
        return CryptoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CryptoViewHolder, position: Int) {
        val crypto = cryptoList[position]
        holder.bind(crypto)
    }

    override fun getItemCount(): Int {
        return cryptoList.size
    }

    fun setCryptoList(cryptoList: List<CurrencyCombinedData>) {
        this.cryptoList = cryptoList
        notifyDataSetChanged()
    }

    class CryptoViewHolder(private val binding: ItemCurrencyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(crypto: CurrencyCombinedData) {
            binding.currencyFullName.text = crypto.currencyInfo.nameFull
            binding.exchangeRate.text = "\$ ${crypto.exchangeRate}"

            Glide.with(itemView).load(crypto.currencyInfo.iconUrl).error(R.drawable.error)
                .placeholder(R.drawable.placeholder).into(binding.currencyIcon)
        }
    }
}
