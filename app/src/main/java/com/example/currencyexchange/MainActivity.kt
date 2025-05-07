package com.example.currencyexchange

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
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

@Preview(showBackground = true)
@Composable
fun CurrencyExchangeApp(viewModel: CurrencyViewModel = viewModel()) {
    var amount by remember { mutableStateOf("") }
    var fromCurrency by remember { mutableStateOf("EUR") }
    var toCurrency by remember { mutableStateOf("USD") }
    var message by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }

    val currencies = viewModel.rates.keys.toList()
    val context = LocalContext.current
    Column(modifier = Modifier.padding(16.dp)) {
        Text("MY BALANCES")
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = viewModel.userBalance.entries.joinToString("     ") { "${it.key}=${it.value}" }.removeSurrounding("{","}"))
        Spacer(modifier = Modifier.height(20.dp))
        Row (Modifier.fillMaxWidth()){
            Image(painter = painterResource(id = R.drawable.ic_sell), contentDescription ="sell icon" ,
                modifier = Modifier.padding(top = 16.dp))
            Text(text = "Sell",
                modifier = Modifier
                    .width(150.dp)
                    .padding(top = 20.dp))
            TextField(
                value = amount,
                onValueChange = { amount = it},
                label = { Text("")},
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent, // Background when not focused
                    focusedContainerColor = Color.Transparent // Background when focused
                ),
                modifier = Modifier
                    .width(100.dp)

            )
            DropdownMenuPicker("From Currency", currencies, fromCurrency){ fromCurrency = it}
        }
        Row(Modifier.fillMaxWidth()) {
            Image(painter = painterResource(id = R.drawable.ic_receive), contentDescription ="receive icon" ,
                modifier = Modifier.padding(top = 16.dp))
            Text(text = "Receive",
                modifier = Modifier
                    .width(150.dp)
                    .padding(top = 20.dp))
            TextField(
                value = "",
                onValueChange = { amount = it},
                label = { Text("")},colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent, // Background when not focused
                    focusedContainerColor = Color.Transparent // Background when focused
                ),
                modifier = Modifier
                    .width(100.dp)

            )

            DropdownMenuPicker("To Currency", currencies, toCurrency){ toCurrency = it }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(onClick = {
            val amountDouble = amount.toDoubleOrNull()
            message = if (amountDouble != null){
                viewModel.convertCurrency(amountDouble,fromCurrency,toCurrency)
            }else {
                "Invalid amount"
            }
            showDialog =true
        }, modifier = Modifier.align(Alignment.CenterHorizontally)
            .width(200.dp)
        ) {
            Text("Convert")
        }
        CustomDialog(showDialog = showDialog, onDismiss = {showDialog = false}, message)
        Spacer(modifier = Modifier.height(10.dp))

       // Text(text = message, style = MaterialTheme.typography.bodySmall)
    }
}

@Composable
fun DropdownMenuPicker(label: String,
                       options: List<String>,
                       selectedOption: String,
                       onOptionSelected: (String) -> Unit)
{
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.padding(top = 18.dp)){
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

@Composable
fun CustomDialog(showDialog: Boolean, onDismiss: () -> Unit, message:String) {
    if (showDialog) {
        Dialog(onDismissRequest = onDismiss) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, shape = RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Column {
                    Text(text = "Currency Converted", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = message)
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = onDismiss) {
                        Text("Done")
                    }
                }
            }
        }
    }
}

