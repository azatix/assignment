package changeLicensesTeam

import BaseTest
import api.LicensesApi
import config.HeaderMode.Invalid
import config.HeaderMode.Missing
import config.HeaderMode.Valid
import data.TestConstants.InvalidData.INVALID_CUSTOMER_CODE
import data.TestConstants.InvalidData.INVALID_TEAM
import data.TestConstants.InvalidData.INVALID_TOKEN
import data.TestConstants.LicensesIds.MULTIPLE_LICENSES_LIST
import data.TestConstants.TestTeamIds.TEAM_ONE
import enums.ApiError.INVALID_TOKEN_ERROR
import enums.ApiError.MISSING_TOKEN_HEADER_ERROR
import enums.ApiError.TEAM_NOT_FOUND
import io.ktor.http.HttpStatusCode.Companion.NotFound
import io.ktor.http.HttpStatusCode.Companion.Unauthorized
import io.qameta.allure.Description
import io.qameta.allure.Feature
import kotlinx.coroutines.test.runTest
import model.licenses.customerChangeLicensesTeam.ChangeLicensesTeamRequest
import model.requireErrorBody
import org.junit.jupiter.api.Tag
import org.slf4j.LoggerFactory
import kotlin.test.Test
import kotlin.test.assertEquals

@Tag("changeLicensesTeamTests")
@Feature("Transfer licenses to another team. Negative scenarios")
class ChangeLicensesTeamNegativeTests : BaseTest() {

    private val log = LoggerFactory.getLogger(javaClass)

    private val licensesApi = LicensesApi()

    @Test
    @Description("Test /customer/changeLicensesTeam endpoint with invalid token and customer code")
    fun changeLicensesTeam_whenHeadersAreInvalid_shouldReturnInvalidToken() = runTest {

        log.info("Preparing request with valid licenses $MULTIPLE_LICENSES_LIST and team $TEAM_ONE")
        val request = ChangeLicensesTeamRequest(MULTIPLE_LICENSES_LIST, TEAM_ONE)

        log.info("Making request with invalid token and customer code")
        val response = licensesApi.customerChangeLicensesTeam(
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
    @Description("Test /customer/changeLicensesTeam endpoint without token")
    fun changeLicensesTeam_whenTokenIsAbsent_shouldReturnMissingToken() = runTest {

        log.info("Preparing request with licenses $MULTIPLE_LICENSES_LIST and team $TEAM_ONE")
        val request = ChangeLicensesTeamRequest(MULTIPLE_LICENSES_LIST, TEAM_ONE)

        log.info("Making request with missing token")
        val response = licensesApi.customerChangeLicensesTeam(
            request = request,
            headerMode = Missing
        )

        assertEquals(Unauthorized, response.status)

        assertEquals(
            MISSING_TOKEN_HEADER_ERROR.code,
            response.requireErrorBody().code,
            "Response should return code $MISSING_TOKEN_HEADER_ERROR"
        )
        assertEquals(
            "X-Api-Key header is required",
            response.requireErrorBody().description,
            "Response should indicate that required header is missing"
        )
    }

    @Test
    @Description("Test /customer/changeLicensesTeam without required Team value")
    fun changeLicensesTeam_whenTeamIsAbsent_shouldReturnTeamNotFound() = runTest {

        log.info("Preparing request with licenses $MULTIPLE_LICENSES_LIST and without team")
        val request = ChangeLicensesTeamRequest(MULTIPLE_LICENSES_LIST)

        log.info("Making request without required team value")
        val response = licensesApi.customerChangeLicensesTeam(
            request = request,
            headerMode = Valid
        )

        assertEquals(NotFound, response.status)

        assertEquals(
            TEAM_NOT_FOUND.code,
            response.requireErrorBody().code,
            "Response should return code $TEAM_NOT_FOUND"
        )
    }

    @Test
    @Description("Test /customer/changeLicensesTeam with invalid Team value")
    fun changeLicensesTeam_whenTeamIsInvalid_shouldReturnTeamNotFound() = runTest {

        log.info("Preparing request with licenses $MULTIPLE_LICENSES_LIST and invalid team $INVALID_TEAM")
        val request = ChangeLicensesTeamRequest(MULTIPLE_LICENSES_LIST)

        log.info("Making request with invalid team value")
        val response = licensesApi.customerChangeLicensesTeam(
            request = request,
            headerMode = Valid
        )

        assertEquals(NotFound, response.status)

        assertEquals(
            TEAM_NOT_FOUND.code,
            response.requireErrorBody().code,
            "Response should return code $TEAM_NOT_FOUND"
        )
    }
}