package com.example.currencyexchange

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.PopupProperties
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
    var convertedAmount by remember { mutableStateOf("") }
    var fromCurrency by remember { mutableStateOf("EUR") }
    var toCurrency by remember { mutableStateOf("USD") }
    var message by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }

    val currencies = viewModel.rates.keys.toList()
    val context = LocalContext.current
    Column(modifier = Modifier.padding(16.dp,30.dp)) {
        Text("MY BALANCES", color = Color.Gray,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = viewModel.userBalance.entries
            .joinToString("     ") { "${it.value} ${it.key}" }
                .removeSurrounding("{","}"),
            fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(50.dp))
        Text("CURRENCY EXCHANGE", color = Color.Gray,
            fontWeight = FontWeight.Bold
        )
        Row (
            Modifier
                .fillMaxWidth()
                .padding(16.dp)){
            Image(painter = painterResource(id = R.drawable.ic_sell), contentDescription ="sell icon" ,
                modifier = Modifier.padding(top = 30.dp))
            Text(text = "Sell",
                modifier = Modifier
                    .width(150.dp)
                    .padding(top = 35.dp, start = 5.dp))
            TextField(
                value = amount,
                onValueChange = { amount = it},
                label = { Text("")},
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent, // Background when not focused
                    focusedContainerColor = Color.Transparent, // Background when focused
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .width(100.dp)
            )
            DropdownMenuPicker("From", currencies, fromCurrency){ fromCurrency = it}
            //CustomDropdownMenu(currencies, "From",Color.Black,Modifier){ fromCurrency = it}
        }
        HorizontalDivider(modifier = Modifier.padding(start = 8.dp, end = 8.dp),
            color = Color.LightGray,
            thickness = 1.dp
        )
        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)) {
            Image(painter = painterResource(id = R.drawable.ic_receive), contentDescription ="receive icon" ,
                modifier = Modifier.padding(top = 30.dp))
            Text(text = "Receive",
                modifier = Modifier
                    .width(150.dp)
                    .padding(top = 35.dp, start = 5.dp))
            Text(text = convertedAmount, modifier = Modifier
                .width(100.dp)
                .padding(top = 20.dp))

            DropdownMenuPicker("To", currencies, toCurrency){ toCurrency = it
                val amountDouble = amount.toDoubleOrNull()
                if (amountDouble != null) {
                    convertedAmount = viewModel.converter(amountDouble, fromCurrency, toCurrency).toString()
                } }

    /*        CustomDropdownMenu(currencies, "To",Color.Black,Modifier){ toCurrency = it
                val amountDouble = amount.toDoubleOrNull()
                if (amountDouble != null) {
                    convertedAmount = viewModel.converter(amountDouble, fromCurrency, toCurrency).toString()
                } }*/
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(onClick = {
            val amountDouble = amount.toDoubleOrNull()
            amount = ""
            convertedAmount = ""
            message = if (amountDouble != null){
                viewModel.convertCurrency(amountDouble,fromCurrency,toCurrency)
            }else {
                "Invalid amount"
            }
            showDialog =true
        }, modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .width(300.dp)
            .height(110.dp)
            .padding(top = 50.dp)
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
        Row (Modifier.width(500.dp)){
        TextButton(onClick = { expanded = true }) {

                Text(selectedOption)
                Image(painter = painterResource(id = R.drawable.arrow_down),
                    contentDescription ="arrow")
                }
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
fun CustomDropdownMenu(
    list: List<String>, // Menu Options
    defaultSelected: String, // Default Selected Option on load
    color: Color, // Color
    modifier: Modifier, //
    onSelected: (String) -> Unit, // Pass the Selected Option
) {
    var selectedIndex by remember { mutableStateOf("") }
    var expand by remember { mutableStateOf(false) }
    var stroke by remember { mutableStateOf(1) }
    Box(
        modifier
            .padding(8.dp)
            .clickable {
                expand = true
                stroke = if (expand) 2 else 1
            },
        contentAlignment = Alignment.Center
    ) {

        Text(
            text = defaultSelected,
            color = color,
            fontSize = 10.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
        )

        DropdownMenu(
            expanded = expand,
            onDismissRequest = {
                expand = false
                stroke = if (expand) 2 else 1
            },
            properties = PopupProperties(
                focusable = false,
                dismissOnBackPress = true,
                dismissOnClickOutside = true,
            ),
            modifier = Modifier
                .background(White)
                .padding(2.dp)
                .fillMaxWidth(.4f)
        ) {
            list.forEach {  item ->
                DropdownMenuItem(
                    onClick = {
                        selectedIndex = item
                        expand = false
                        stroke = if (expand) 2 else 1
                        onSelected(selectedIndex)
                    },
                    text = {
                        Text(
                            text = item,
                            color = color,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                )
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

