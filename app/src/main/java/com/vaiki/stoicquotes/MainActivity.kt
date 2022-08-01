package com.vaiki.stoicquotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
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
        val linearProgressBar = findViewById<ProgressBar>(R.id.lpb_pagination)
        val circularProgressBar = findViewById<ProgressBar>(R.id.cpb_pagination)
        val tvError = findViewById<TextView>(R.id.tv_error_connection)
        val btnTryAgain = findViewById<Button>(R.id.btn_try_again)

        getQuote(tvQuote,linearProgressBar, circularProgressBar,tvError, btnTryAgain)
        tvQuote.setOnClickListener {
            getQuote(tvQuote, linearProgressBar,circularProgressBar, tvError, btnTryAgain)
        }
        btnTryAgain.setOnClickListener {
            getQuote(tvQuote, linearProgressBar, circularProgressBar,tvError, btnTryAgain)
        }
    }

    private fun getQuote(
        tvQuote: TextView,
        linearProgressBar: ProgressBar,
        circularProgressBar: ProgressBar,
        tvError: TextView,
        btnTryAgain: Button
    ) {
        viewModel.getRandomQuote()
        viewModel.errorMessage.observe(this) { it ->
            if (it) {
                tvError.visibility = View.VISIBLE
                btnTryAgain.visibility = View.VISIBLE
                tvQuote.visibility = View.INVISIBLE
                linearProgressBar.visibility = View.INVISIBLE
                circularProgressBar.visibility = View.VISIBLE
            } else {
                tvQuote.visibility = View.VISIBLE
                tvError.visibility = View.INVISIBLE
                btnTryAgain.visibility = View.INVISIBLE
                circularProgressBar.visibility = View.INVISIBLE
                viewModel.quoteItem.observe(this) { response ->
                    when (response) {
                        is Resource.Success -> {
                            linearProgressBar.visibility = View.INVISIBLE
                            tvQuote.text = response.data?.body.toString()
                        }
                        is Resource.Loading -> {
                            linearProgressBar.visibility = View.VISIBLE
                        }
                        is Resource.Error -> {
                            response.message?.let { message ->
                                tvQuote.text = message
                            }
                        }
                    }
                }
            }
        }
    }
}


