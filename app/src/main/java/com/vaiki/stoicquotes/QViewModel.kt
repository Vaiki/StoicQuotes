package com.vaiki.stoicquotes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaiki.stoicquotes.api.QuoteItem
import com.vaiki.stoicquotes.api.RetrofitService
import kotlinx.coroutines.launch

class QViewModel : ViewModel() {
     private val _quoteItem = MutableLiveData<QuoteItem>()
    init{
        getRandomQuote()
    }
   val quoteItem:LiveData<QuoteItem> = _quoteItem
    fun getRandomQuote() {
        viewModelScope.launch {
            val response = RetrofitService.create().randomQuotes()
            if (response.isSuccessful) {
                _quoteItem.postValue(response.body())
            }
        }

    }
}