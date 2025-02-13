package com.example.currencyexchange.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyexchange.network.RetrofitInstance
import kotlinx.coroutines.launch

class CurrencyViewModel : ViewModel(){
    var rates: Map<String,Double> = emptyMap()
    var userBalance: MutableMap<String, Double> = mutableMapOf("EUR" to 1000.0)
    var conversionCount = 0
    val freeConversions = 5
    val commissionRate = 0.007

    init {
        fetchExchangeRates()
    }

    private fun fetchExchangeRates() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getExchangeRates()
                rates = response.rates
            }catch (e : Exception){
                e.printStackTrace()
            }
        }
    }
    fun convertCurrency(amount: Double, from : String, to :String) : String
    {
        if (!rates.containsKey(to) || !rates.containsKey(from))
            return "Currency not support!"

        if ((userBalance[from] ?: 0.0) < amount)
            return "Insufficient Balance!"

        val rate = rates[to]!! / rates[from]!!
        val convertedAmount = amount * rate

        var commission = 0.0
        if (conversionCount >= freeConversions)
            commission = amount * commissionRate

        val newFromBalance = (userBalance[from] ?: 0.0) - amount - commission
        val newToBalance = (userBalance[to] ?: 0.0) + convertedAmount

        if (newFromBalance < 0)
            return "Transaction would result in negative balance!"

        userBalance[from] = newFromBalance
        userBalance[to] = newToBalance
        conversionCount++

        return "You converted $amount $from to ${"%.2f".format(convertedAmount)} $to." +
                " Commission Fee : ${"%.2f".format(commission)} $from."
    }
}

