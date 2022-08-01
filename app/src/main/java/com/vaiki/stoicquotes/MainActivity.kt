package com.vaiki.stoicquotes

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels

const val TAG = "STOIC"
class MainActivity : AppCompatActivity() {
    private val viewModel: QViewModel by viewModels()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tvQuote = findViewById<TextView>(R.id.tv_quote)
        val paginationProgressBar = findViewById<ProgressBar>(R.id.pb_pagination)
        getQuote(tvQuote, paginationProgressBar)
        tvQuote.setOnClickListener {
            getQuote(tvQuote, paginationProgressBar)
        }
    }

    private fun getQuote(tvQuote: TextView, paginationProgressBar: ProgressBar) {
        viewModel.getRandomQuote()
        viewModel.quoteItem.observe(this) { response ->
            when (response) {
                is Resource.Success -> {
                    paginationProgressBar.visibility = View.INVISIBLE
                    tvQuote.text = response.data?.body.toString()

                }
                is Resource.Loading -> {
                    paginationProgressBar.visibility = View.VISIBLE
                }
                is Resource.Error -> {
                    tvQuote.text = "Проверьте интернет соединение"
                    response.message?.let { message ->
                        Log.e(TAG, "An error occurred: $message")
                    }
                }
            }
        }
    }
}

