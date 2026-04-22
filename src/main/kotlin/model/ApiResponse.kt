package model

import io.ktor.http.*

data class ApiResponse<T>(
    val status: HttpStatusCode,
    val body: T?
)

fun <T> ApiResponse<T>.requireBody(): T =
    body ?: error("Response body is missing. Status: $status")

fun <T> ApiResponse<T>.requireSuccessBody(): T {
    require(status.value in 200..299) {
        "Expected success status, but got $status"
    }
    return requireBody()
}

fun <T> ApiResponse<T>.requireErrorBody(): T {
    require(status.value >= 400) {
        "Expected error status, but got $status"
    }
    return requireBody()
}