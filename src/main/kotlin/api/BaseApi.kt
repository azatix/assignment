package api

import config.HttpClientConfig
import config.HttpClientFactory

open class BaseApi {
    protected val client = HttpClientFactory.client
    protected val baseUrl = HttpClientConfig.BASE_URL
}