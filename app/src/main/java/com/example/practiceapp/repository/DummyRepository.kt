package com.example.practiceapp.repository

import com.example.practiceapp.remote.QuotesApi
import com.example.practiceapp.remote.RetrofitHelper

class DummyRepository {
    private val quotesApi = RetrofitHelper.getInstance().create(QuotesApi::class.java)
    suspend fun getData() = quotesApi.getQuotes()
}