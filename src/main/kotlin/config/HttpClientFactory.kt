package config

import config.Secrets.apiKey
import config.Secrets.customerCode
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

private const val CUSTOMER_CODE = "X-Customer-Code"
private const val API_KEY = "X-Api-Key"

object HttpClientFactory {

    val client = create()

    private fun create(): HttpClient {
        return HttpClient(CIO) {
            expectSuccess = false

            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        isLenient = true
                        explicitNulls = false
                    }
                )
            }
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL

                sanitizeHeader { header ->
                    header == "X-Api-Key" || header == "X-Customer-Code"
                }
            }
        }
    }

    fun close() {
        client.close()
    }
}

fun HttpRequestBuilder.applyTestHeaders(mode: HeaderMode) {
    headers.remove(CUSTOMER_CODE)
    headers.remove(API_KEY)

    when (mode) {
        is HeaderMode.Valid -> {
            headers[CUSTOMER_CODE] = customerCode
            headers[API_KEY] = apiKey
        }

        is HeaderMode.Missing -> {
        }

        is HeaderMode.Invalid -> {
            headers[CUSTOMER_CODE] = mode.customerCodeHeader ?: customerCode
            headers[API_KEY] = mode.apiKeyHeader ?: apiKey
        }

        is HeaderMode.Custom -> {
            mode.headers.forEach { (name, value) ->
                if (value == null) {
                    headers.remove(name)
                } else {
                    headers[name] = value
                }
            }
        }
    }
}