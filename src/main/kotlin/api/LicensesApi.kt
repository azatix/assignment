package api

import config.HeaderMode
import config.HeaderMode.Valid
import config.HttpClientFactory
import config.applyTestHeaders
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.http.ContentType.Application.Json
import model.ApiResponse
import model.licenses.customerChangeLicensesTeam.ChangeLicensesTeamRequest
import model.licenses.customerChangeLicensesTeam.ChangeLicensesTeamResponse
import model.licenses.customerLicensesAssign.LicensesAssignRequest
import model.licenses.customerLicensesAssign.LicensesAssignResponse
import org.slf4j.LoggerFactory

class LicensesApi(private val client: HttpClient = HttpClientFactory.client) : BaseApi() {

    private val log = LoggerFactory.getLogger(javaClass)

    suspend fun customerChangeLicensesTeam(
        request: ChangeLicensesTeamRequest,
        headerMode: HeaderMode = Valid
    ): ApiResponse<ChangeLicensesTeamResponse> {
        log.info("Calling endpoint POST /customer/changeLicensesTeam")

        val response = client.post("$baseUrl/customer/changeLicensesTeam") {
            applyTestHeaders(headerMode)
            contentType(Json)
            setBody(request)
        }

        val body = response.safeBody<ChangeLicensesTeamResponse>()

        return ApiResponse(response.status, body)
    }

    suspend fun customerLicensesAssign(
        request: LicensesAssignRequest,
        headerMode: HeaderMode = Valid
    ): ApiResponse<LicensesAssignResponse> {
        log.info("Calling endpoint POST /customer/licenses/assign")

        val response = client.post("$baseUrl/customer/licenses/assign") {
            applyTestHeaders(headerMode)
            contentType(Json)
            setBody(request)
        }

        val body = response.safeBody<LicensesAssignResponse>()

        return ApiResponse(response.status, body)
    }

    suspend inline fun <reified T> HttpResponse.safeBody(): T? {
        return try {
            if (bodyAsText().isBlank()) null
            else body()
        } catch (_: Exception) {
            null
        }
    }
}
