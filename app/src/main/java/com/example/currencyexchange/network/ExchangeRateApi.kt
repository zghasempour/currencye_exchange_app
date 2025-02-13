package com.example.currencyexchange.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

data class ExchangeRateResponse(
    val base : String,
    val date : String,
    val rates : Map<String,Double>
)

interface ExchangeRateApi{
    @GET("tasks/api/currency-exchange-rates")
    suspend fun getExchangeRates() : ExchangeRateResponse
}

object RetrofitInstance {
    private const val BASE_URL = "https://developers.paysera.com/"

    val api : ExchangeRateApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ExchangeRateApi::class.java)
    }
}