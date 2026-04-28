package api

import config.HttpClientConfig

open class BaseApi {
    protected val baseUrl = HttpClientConfig.BASE_URL
}