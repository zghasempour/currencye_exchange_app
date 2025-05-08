

<h1>Multi-Currency Exchange App 🏦💱</h1>
<hr>

<h2>Overview</h2>
<p>This is a Kotlin-based Android application that allows users to exchange currencies in a multi-currency account. It retrieves real-time exchange rates from an API, ensures the user cannot go into a negative balance, and applies a commission fee after the first five free exchanges.</p>

<h2>Features 🚀</h2>
<ul>
    <li>Multi-currency support - Convert any currency using real-time rates.</li>
    <li>Live Exchange Rates - Updates rates every 5 seconds using a public API.</li>
    <li>Balance Management - Users cannot have a negative balance.</li>
    <li>Commission Fee System - The first 5 exchanges are free, after which a 0.7% fee applies.</li>
    <li>Jetpack Compose UI - Modern UI for better performance and flexibility.</li>
</ul>
<hr>

<img src="https://github.com/user-attachments/assets/574aa59d-93fa-4623-9529-34d84c680da9" alt="My Image" width="400">
<img src="https://github.com/user-attachments/assets/9f7d516d-6cc6-49d6-93b4-81fce8b22685" alt="My Image" width="400">
<img src="https://github.com/user-attachments/assets/1c93bc99-d390-4dcf-b690-7ebda9a158f0" alt="My Image" width="400">
<img src="https://github.com/user-attachments/assets/b78fae93-2e1a-4e97-9755-a065472ad927" alt="My Image" width="400">


<h2>How It Works 🔄</h2>
<ol>
    <li>Starting Balance: The user starts with 1000 EUR.</li>
    <li>Currency Exchange:
        <ul class="sub-list">
            <li>User enters the amount to exchange.</li>
            <li>Selects the currency to sell and currency to buy.</li>
            <li>Clicks Submit to process the transaction.</li>
        </ul>
    </li>
    <li>Balance Update: The system checks if the user has enough balance before converting.</li>
    <li>Commission Fee: After 5 exchanges, a 0.7% fee is applied and deducted separately.</li>
    <li>User Notification: A message shows the converted amount, updated balance, and any commission fee.</li>
</ol>
<hr>

<h2>Technology Stack 🛠</h2>
<ul>
    <li>Language: Kotlin</li>
    <li>UI Framework: Jetpack Compose</li>
    <li>State Management: ViewModel</li>
    <li>Networking: Retrofit for API calls</li>
    <li>Coroutines: For background data fetching</li>
    <li>LiveData/StateFlow: To update UI reactively</li>
</ul>
<hr>

<h2>Code Structure 🏗</h2>
<div class="code-structure">
📦 MultiCurrencyExchange<br>
 ┣ 📂 ui            # Jetpack Compose UI components<br>
 ┣ 📂 data          # API handling & data models<br>
 ┣ 📂 viewmodel     # Business logic & state management<br>
 ┣ 📜 MainActivity.kt  # Entry point of the app<br>
 ┗ 📜 README.md     # Project documentation  
</div>
<hr>

<h2>Key Components</h2>
<ul>
    <li>📌 MainActivity.kt - Hosts the Compose UI</li>
    <li>📌 ExchangeViewModel.kt - Manages currency exchange logic</li>
    <li>📌 ExchangeRatesRepository.kt - Fetches exchange rates using Retrofit</li>
    <li>📌 BalanceManager.kt - Handles user balance and transactions</li>
</ul>
<hr>

<h2>Commission Fee Logic 💰</h2>
<ul>
    <li>The first 5 transactions are free.</li>
    <li>From the 6th transaction onwards, a 0.7% commission is charged.</li>
    <li>The fee is deducted separately from both currencies.</li>
</ul>

<h3>Example:</h3>
<p>You have converted 100.00 EUR to 110.00 USD.<br>
Commission Fee - 0.70 EUR.</p>

<hr>

<h2>Example Transactions 📝</h2>

<h3>1️⃣ First 5 Transactions (No Fee)</h3>
<p>User has 1000 EUR → Converts 100 EUR to USD</p>
<p>✅ Result:</p>
<p>You have converted 100.00 EUR to 112.90 USD.<br>
New Balance: 900.00 EUR, 112.90 USD.<br>
No commission fee is applied.</p>

<h3>2️⃣ After 5 Transactions (With Fee)</h3>
<p>User has 900 EUR → Converts 100 EUR to USD<br>
⚠️ 0.7% fee applies (0.70 EUR)</p>
<p>✅ Result:</p>
<p>You have converted 100.00 EUR to 112.90 USD.<br>
Commission Fee - 0.70 EUR.<br>
New Balance: 899.30 EUR, 112.90 USD.</p>
<hr>

<h2>Error Handling & Validation ⚠️</h2>
<ul>
    <li>🚫 Insufficient Balance - <strong>Error:</strong> Not enough balance to complete this exchange.</li>
    <li>🚫 Invalid API Response - <strong>Error:</strong> Failed to fetch exchange rates. Please try again.</li>
</ul>




