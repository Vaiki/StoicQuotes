package com.vaiki.stoicquotes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaiki.stoicquotes.api.QuoteItem
import com.vaiki.stoicquotes.api.RetrofitService
import kotlinx.coroutines.launch
import retrofit2.Response

class QViewModel : ViewModel() {
    val quoteItem = MutableLiveData<Resource<QuoteItem>>()

    fun getRandomQuote() {
        viewModelScope.launch {
            quoteItem.postValue(Resource.Loading())
            val response = RetrofitService.create().randomQuotes()
            quoteItem.postValue(handleResponse(response))
        }
    }

    private fun handleResponse(response: Response<QuoteItem>): Resource<QuoteItem> {
        if (response.isSuccessful) {
            return Resource.Success(response.body()!!)
        }
        return Resource.Error(response.message())
    }
}