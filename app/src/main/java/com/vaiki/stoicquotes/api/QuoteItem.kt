package com.vaiki.stoicquotes.api

data class QuoteItem(
    val author: String,
    val author_id: Int,
    val body: String,
    val id: Int
)