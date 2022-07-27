package com.vaiki.stoicquotes

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels


class MainActivity : AppCompatActivity() {
    private val viewModel: QViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tvQuote = findViewById<TextView>(R.id.tv_quote)

        tvQuote.setOnClickListener {
            getQuote(tvQuote)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun getQuote(tvQuote: TextView) {
        if (isNetworkAvailable(this)){
            viewModel.getRandomQuote()
            viewModel.quoteItem.observe(this) {
                tvQuote.text = "\"${it.body}\""
            }
        }

    }
    fun isNetworkAvailable(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}