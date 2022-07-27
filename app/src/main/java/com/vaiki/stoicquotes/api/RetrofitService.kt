package com.vaiki.stoicquotes.api

import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface RetrofitService {

    @GET("v1/api/quotes/random")
    suspend fun randomQuotes():Response<QuoteItem>

    companion object {
        private const val BASE_URL = " https://stoicquotesapi.com/"
        fun create(): RetrofitService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(RetrofitService::class.java)
        }
    }
}