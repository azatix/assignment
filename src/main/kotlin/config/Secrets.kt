package config

object Secrets {
    val customerCode: String = System.getenv("CUSTOMER_CODE") ?: error("Variable CUSTOMER_CODE is not set")
    val apiKey: String = System.getenv("API_KEY") ?: error("Variable API_KEY is not set")
}