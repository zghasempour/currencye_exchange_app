package com.example.currencyexchange

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.currencyexchange.viewmodel.CurrencyViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
         CurrencyExchangeApp()
        }
    }
}

@Composable
fun CurrencyExchangeApp(viewModel: CurrencyViewModel = viewModel()) {
    var amount by remember { mutableStateOf("") }
    var fromCurrency by remember { mutableStateOf("EUR") }
    var toCurrency by remember { mutableStateOf("USD") }
    var message by remember { mutableStateOf("") }

    val currencies = viewModel.rates.keys.toList()

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Your Balance : ${viewModel.userBalance}")
        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it},
            label = { Text("Enter Amount")}
        )
        DropdownMenuPicker("From Currency", currencies, fromCurrency){ fromCurrency = it}
        DropdownMenuPicker("To Currency", currencies, toCurrency){ toCurrency = it}

        Spacer(modifier = Modifier.height(10.dp))

        Button(onClick = {
            val amountDouble = amount.toDoubleOrNull()
            message = if (amountDouble != null){
                viewModel.convertCurrency(amountDouble,fromCurrency,toCurrency)
            }else {
                "Invalid amount"
            }
        },
            modifier = Modifier.height(10.dp)
        ) {
            Text("Convert")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(text = message, style = MaterialTheme.typography.bodySmall)
    }
}

@Composable
fun DropdownMenuPicker(label: String,
                       options: List<String>,
                       selectedOption: String,
                       onOptionSelected: (String) -> Unit)
{
    var expanded by remember { mutableStateOf(false) }

    Box{
        TextButton(onClick = { expanded = true }) {
            Text(selectedOption)
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { option ->
                DropdownMenuItem(text = {Text(text = option)} , onClick = {
                    onOptionSelected(option)
                    expanded = false
                })
            }
        }
    }
}

