Multi-Currency Exchange App 🏦💱
_________________________________________________________________________________________________________________
Overview

This is a Kotlin-based Android application that allows users to exchange currencies in a multi-currency account. It retrieves real-time exchange rates from an API, ensures the user cannot go into a negative balance, and applies a commission fee after the first five free exchanges.

Features 🚀

	✅ Multi-currency support - Convert any currency using real-time rates.
	✅ Live Exchange Rates - Updates rates every 5 seconds using a public API.
	✅ Balance Management - Users cannot have a negative balance.
	✅ Commission Fee System - The first 5 exchanges are free, after which a 0.7% fee applies.
	✅ Jetpack Compose UI - Modern UI for better performance and flexibility.
_________________________________________________________________________________________________________________
How It Works 🔄
		1.	Starting Balance: The user starts with 1000 EUR.
		2.	Currency Exchange:
			•	User enters the amount to exchange.
			•	Selects the currency to sell and currency to buy.
			•	Clicks Submit to process the transaction.
		3.	Balance Update: The system checks if the user has enough balance before converting.
		4.	Commission Fee: After 5 exchanges, a 0.7% fee is applied and deducted separately.
		5.	User Notification: A message shows the converted amount, updated balance, and any commission fee.
_________________________________________________________________________________________________________________
Technology Stack 🛠
	•	Language: Kotlin
	•	UI Framework: Jetpack Compose
	•	State Management: ViewModel
	•	Networking: Retrofit for API calls
	•	Coroutines: For background data fetching
	•	LiveData/StateFlow: To update UI reactively
_________________________________________________________________________________________________________________
Code Structure 🏗

📂 Main Files & Directories

📦 MultiCurrencyExchange
 ┣ 📂 ui            # Jetpack Compose UI components  
 ┣ 📂 data          # API handling & data models  
 ┣ 📂 viewmodel     # Business logic & state management  
 ┣ 📜 MainActivity.kt  # Entry point of the app  
 ┗ 📜 README.md     # Project documentation  
_________________________________________________________________________________________________________________
Key Components

📌 MainActivity.kt - Hosts the Compose UI
📌 ExchangeViewModel.kt - Manages currency exchange logic
📌 ExchangeRatesRepository.kt - Fetches exchange rates using Retrofit
📌 BalanceManager.kt - Handles user balance and transactions
_________________________________________________________________________________________________________________
Commission Fee Logic 💰
	•	The first 5 transactions are free.
	•	From the 6th transaction onwards, a 0.7% commission is charged.
	•	The fee is deducted separately from both currencies.
	•	Example:

You have converted 100.00 EUR to 110.00 USD.
Commission Fee - 0.70 EUR.

Example Transactions 📝

1️⃣ First 5 Transactions (No Fee)

User has 1000 EUR → Converts 100 EUR to USD
✅ Result:

You have converted 100.00 EUR to 112.90 USD.
New Balance: 900.00 EUR, 112.90 USD.
No commission fee is applied.

2️⃣ After 5 Transactions (With Fee)

User has 900 EUR → Converts 100 EUR to USD
⚠️ 0.7% fee applies (0.70 EUR)
✅ Result:

You have converted 100.00 EUR to 112.90 USD.
Commission Fee - 0.70 EUR.
New Balance: 899.30 EUR, 112.90 USD.
_________________________________________________________________________________________________________________
Error Handling & Validation ⚠️

🚫 Insufficient Balance

Error: Not enough balance to complete this exchange.

🚫 Invalid API Response

Error: Failed to fetch exchange rates. Please try again.

