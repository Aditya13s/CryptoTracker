package com.aditya.cryptotracker.ui

import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.aditya.cryptotracker.R
import com.aditya.cryptotracker.adapter.CurrencyAdapter
import com.aditya.cryptotracker.databinding.ActivityMainBinding
import com.aditya.cryptotracker.utils.Result
import com.aditya.cryptotracker.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var currencyAdapter: CurrencyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setTitle()
        initView()
        observeViewModel()
    }

    private fun setTitle() {
        val titleText = SpannableStringBuilder("Crypto List")
        titleText.setSpan(
            StyleSpan(Typeface.BOLD), 0, titleText.length, Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )
        titleText.setSpan(
            ForegroundColorSpan(getColor(R.color.primaryColor)),
            0,
            titleText.length,
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )
        title = titleText
    }

    private fun initView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = currencyAdapter

            val dividerItemDecoration =
                DividerItemDecoration(binding.recyclerView.context, LinearLayoutManager.VERTICAL)
            binding.recyclerView.addItemDecoration(dividerItemDecoration)
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshData()
        }
    }

    private fun observeViewModel() {
        viewModel.currencyCombinedData.observe(this) { result ->
            when (result) {
                is Result.Success -> {
                    currencyAdapter.setCryptoList(result.data)
                    binding.swipeRefreshLayout.isRefreshing = false
                }

                is Result.Error -> {
                    binding.lastRefreshTime.text = result.message
                    binding.swipeRefreshLayout.isRefreshing = false
                }

                is Result.Loading -> {
                    binding.lastRefreshTime.text = "Loading..."
                }
            }
        }

        viewModel.lastRefreshTime.observe(this) { lastRefreshTime ->
            binding.lastRefreshTime.text = lastRefreshTime
        }

    }
}
