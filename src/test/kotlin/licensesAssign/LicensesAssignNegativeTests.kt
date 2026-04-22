package licensesAssign

import BaseTest
import api.LicensesApi
import config.HeaderMode.Invalid
import config.HeaderMode.Valid
import data.LicensesAssignRequestFactory
import data.TestConstants.InvalidData.INSUFFICIENT_PERMISSIONS_TOKEN
import data.TestConstants.InvalidData.INVALID_CUSTOMER_CODE
import data.TestConstants.InvalidData.INVALID_TOKEN
import data.TestConstants.InvalidData.MISMATCH_TOKEN
import data.TestConstants.TestTeamIds.TEAM_ONE
import enums.ApiError.INSUFFICIENT_PERMISSIONS
import enums.ApiError.INVALID_CONTACT_EMAIL
import enums.ApiError.INVALID_TOKEN_ERROR
import enums.ApiError.LICENSE_IS_NOT_AVAILABLE_TO_ASSIGN
import enums.ApiError.LICENSE_NOT_FOUND
import enums.ApiError.TEAM_MISMATCH
import io.ktor.http.HttpStatusCode.Companion.BadRequest
import io.ktor.http.HttpStatusCode.Companion.Forbidden
import io.ktor.http.HttpStatusCode.Companion.NotFound
import io.ktor.http.HttpStatusCode.Companion.Unauthorized
import io.qameta.allure.Description
import io.qameta.allure.Feature
import kotlinx.coroutines.test.runTest
import model.requireErrorBody
import org.junit.jupiter.api.Tag
import org.slf4j.LoggerFactory
import kotlin.test.Test
import kotlin.test.assertEquals

@Tag("licensesAssignTests")
@Feature("Assign a license to a user. Negative scenarios")
class LicensesAssignNegativeTests : BaseTest() {

    private val log = LoggerFactory.getLogger(javaClass)

    private val licensesApi = LicensesApi()

    @Test
    @Description("Test /customer/licenses/assign endpoint with invalid token and customer code")
    fun assignLicenses_whenHeadersAreInvalid_shouldReturnInvalidToken() = runTest {

        log.info("Preparing valid request")
        val request = LicensesAssignRequestFactory.build()

        log.info("Making request with invalid token and customer code")
        val response = licensesApi.customerLicensesAssign(
            request = request,
            headerMode = Invalid(INVALID_TOKEN, INVALID_CUSTOMER_CODE)
        )

        assertEquals(Unauthorized, response.status)

        assertEquals(
            INVALID_TOKEN_ERROR.code, response.requireErrorBody().code,
            "Response code should return '${INVALID_TOKEN_ERROR}"
        )
        assertEquals(
            "The token provided is invalid", response.requireErrorBody().description,
            "Response should indicate that provided token is invalid"
        )
    }

    @Test
    @Description("Test /customer/licenses/assign endpoint with mismatch token")
    fun assignLicenses_whenTokenIsMismatch_shouldReturnTeamMismatch() = runTest {

        log.info("Preparing request")
        val request = LicensesAssignRequestFactory.build()

        log.info("Making request with mismatch token")
        val response = licensesApi.customerLicensesAssign(
            request = request,
            headerMode = Invalid(MISMATCH_TOKEN)
        )

        assertEquals(Forbidden, response.status)

        assertEquals(
            TEAM_MISMATCH.code, response.requireErrorBody().code,
            "Response code should return '${TEAM_MISMATCH}"
        )
        assertEquals(
            "Token was generated for team with id $TEAM_ONE", response.requireErrorBody().description,
            "Response should indicate that was generated for another team"
        )
    }

    @Test
    @Description("Test /customer/licenses/assign endpoint with invalid email")
    fun assignLicenses_whenEmailInvalid_shouldReturnInvalidContactEmail() = runTest {

        log.info("Preparing request with invalid email")
        val request = LicensesAssignRequestFactory.withInvalidEmail()

        log.info("Making request with invalid email ${request.contact.email}")
        val response = licensesApi.customerLicensesAssign(
            request = request,
            headerMode = Valid
        )

        assertEquals(BadRequest, response.status)

        assertEquals(
            INVALID_CONTACT_EMAIL.code, response.requireErrorBody().code,
            "Response code should return '${INVALID_CONTACT_EMAIL}"
        )
        assertEquals(
            request.contact.email, response.requireErrorBody().description,
            "Response should indicate that provided email is invalid"
        )
    }

    @Test
    @Description("Test /customer/licenses/assign endpoint with expired license")
    fun assignLicenses_whenLicenseIsExpired_shouldReturnLicenseIsNotAssignable() = runTest {

        log.info("Preparing request with expired license")
        val request = LicensesAssignRequestFactory.withExpiredLicenseId()

        log.info("Making request with expired license ${request.licenseId}")
        val response = licensesApi.customerLicensesAssign(
            request = request,
            headerMode = Valid
        )

        assertEquals(BadRequest, response.status)

        assertEquals(
            LICENSE_IS_NOT_AVAILABLE_TO_ASSIGN.code, response.requireErrorBody().code,
            "Response code should return '${LICENSE_IS_NOT_AVAILABLE_TO_ASSIGN}"
        )
    }

    @Test
    @Description("Test /customer/licenses/assign endpoint with invalid license")
    fun assignLicenses_whenLicenseIsInvalid_shouldReturnLicenseIsNotFound() = runTest {

        log.info("Preparing request with invalid license")
        val request = LicensesAssignRequestFactory.withInvalidLicenseId()

        log.info("Making request with invalid license ${request.licenseId}")
        val response = licensesApi.customerLicensesAssign(
            request = request,
            headerMode = Valid
        )

        assertEquals(NotFound, response.status)

        assertEquals(
            LICENSE_NOT_FOUND.code, response.requireErrorBody().code,
            "Response code should return '${LICENSE_NOT_FOUND}"
        )

        assertEquals(
            request.licenseId, response.requireErrorBody().description,
            "Response should indicate invalid licenseId"
        )
    }

    @Test
    @Description("Test /customer/licenses/assign endpoint with empty license")
    fun assignLicenses_whenLicenseIsEmpty_shouldReturnLicenseIsNotFound() = runTest {

        log.info("Preparing request with empty license")
        val request = LicensesAssignRequestFactory.withEmptyLicenseId()

        log.info("Making request with empty license ${request.licenseId}")
        val response = licensesApi.customerLicensesAssign(
            request = request,
            headerMode = Valid
        )

        assertEquals(NotFound, response.status)

        assertEquals(
            LICENSE_NOT_FOUND.code, response.requireErrorBody().code,
            "Response code should return '${LICENSE_NOT_FOUND}"
        )

        assertEquals(
            request.licenseId, response.requireErrorBody().description,
            "Response should indicate invalid licenseId"
        )
    }

    @Test
    @Description("Test /customer/licenses/assign endpoint to assign license to same user second time")
    fun assignLicenses_whenLicenseAssignedToSameUserAgain_shouldReturnLicenseIsNotFound() = runTest {

        log.info("Preparing request with license that already assigned to user")
        val request = LicensesAssignRequestFactory.buildRepeated()

        log.info(
            "Making request with to assign license ${request.licenseId} " +
                    "to user ${request.contact.email} that already has license"
        )
        val response = licensesApi.customerLicensesAssign(
            request = request,
            headerMode = Valid
        )

        assertEquals(BadRequest, response.status)

        assertEquals(
            LICENSE_IS_NOT_AVAILABLE_TO_ASSIGN.code, response.requireErrorBody().code,
            "Response code should return '${LICENSE_IS_NOT_AVAILABLE_TO_ASSIGN}"
        )
    }

    @Test
    @Description("Test /customer/licenses/assign endpoint with insufficient permissions")
    fun assignLicenses_whenAssigningWithInsufficientPermissions_shouldReturnInsufficientPermissions() = runTest {

        log.info("Preparing default request")
        val request = LicensesAssignRequestFactory.build()

        log.info("Making request with without required permissions")
        val response = licensesApi.customerLicensesAssign(
            request = request,
            headerMode = Invalid(INSUFFICIENT_PERMISSIONS_TOKEN)
        )

        assertEquals(Forbidden, response.status)

        assertEquals(
            INSUFFICIENT_PERMISSIONS.code, response.requireErrorBody().code,
            "Response code should return '${INSUFFICIENT_PERMISSIONS}"
        )
    }
}