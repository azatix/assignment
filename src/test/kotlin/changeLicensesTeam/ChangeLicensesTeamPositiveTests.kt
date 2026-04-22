package changeLicensesTeam

import BaseTest
import api.LicensesApi
import config.HeaderMode.Valid
import data.TestConstants.InvalidData.INVALID_LICENSES_LIST
import data.TestConstants.LicensesIds.EMPTY_LICENSES_LIST
import data.TestConstants.LicensesIds.EXPIRED_LICENSE_LIST
import data.TestConstants.LicensesIds.MULTIPLE_LICENSES_LIST
import data.TestConstants.LicensesIds.ONE_LICENSE_LIST
import data.TestConstants.LicensesIds.VALID_LICENSE_LIST
import data.TestConstants.TestTeamIds.TEAM_ONE
import data.TestConstants.TestTeamIds.TEAM_TWO
import io.ktor.http.HttpStatusCode.Companion.OK
import io.qameta.allure.Description
import io.qameta.allure.Feature
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import model.licenses.customerChangeLicensesTeam.ChangeLicensesTeamRequest
import model.requireSuccessBody
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.slf4j.LoggerFactory
import utils.TestDataCleaner
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@TestInstance(PER_CLASS)
@Tag("changeLicensesTeamTests")
@Feature("Transfer licenses to another team. Positive scenarios")
class ChangeLicensesTeamPositiveTests : BaseTest() {

    private val log = LoggerFactory.getLogger(javaClass)

    private val licensesApi = LicensesApi()

    @Test
    @Description("Test /customer/changeLicensesTeam endpoint with invalid list of licenses")
    fun changeLicensesTeam_whenLicensesAreInvalid_shouldReturnEmptyListInResponse() = runTest {

        log.info("Preparing request with invalid list of licenses $INVALID_LICENSES_LIST and team $TEAM_ONE")
        val request = ChangeLicensesTeamRequest(INVALID_LICENSES_LIST, TEAM_ONE)

        log.info("Making request with invalid list of licenses")
        val response = licensesApi.customerChangeLicensesTeam(
            request = request,
            headerMode = Valid
        )

        assertEquals(OK, response.status)

        assertTrue(response.requireSuccessBody().licenseIds.isNullOrEmpty(), "License list should be empty")
    }

    @Test
    @Description("Test /customer/changeLicensesTeam endpoint with empty list of licenses")
    fun changeLicensesTeam_whenLicenseListIsEmpty_shouldReturnEmptyListInResponse() = runTest {

        log.info("Preparing request with empty list of licenses and team $TEAM_ONE")
        val request = ChangeLicensesTeamRequest(EMPTY_LICENSES_LIST, TEAM_ONE)

        log.info("Making request with empty list of licenses")
        val response = licensesApi.customerChangeLicensesTeam(
            request = request,
            headerMode = Valid
        )

        assertEquals(OK, response.status)

        assertTrue(response.requireSuccessBody().licenseIds.isNullOrEmpty(), "License list should be empty")
    }

    @Test
    @Description("Test /customer/changeLicensesTeam endpoint with one value in list of licenses")
    fun changeLicensesTeam_whenOneLicense_shouldReturnSameLicenseInResponse() = runTest {

        log.info("Preparing request with one license $ONE_LICENSE_LIST in list of licenses and team $ONE_LICENSE_LIST")
        val request = ChangeLicensesTeamRequest(ONE_LICENSE_LIST, TEAM_ONE)

        log.info("Making request with one license in list of licenses")
        val response = licensesApi.customerChangeLicensesTeam(
            request = request,
            headerMode = Valid
        )

        assertEquals(OK, response.status)

        assertEquals(ONE_LICENSE_LIST, response.requireSuccessBody().licenseIds, "Lists of licenses should be equal")
    }

    @Test
    @Description("Test /customer/changeLicensesTeam endpoint with multiple values in list of licenses")
    fun changeLicensesTeam_whenMultipleLicenses_shouldReturnSameLicensesInResponse() = runTest {

        log.info("Preparing request with multiple licenses $MULTIPLE_LICENSES_LIST and team $TEAM_ONE")
        val request = ChangeLicensesTeamRequest(MULTIPLE_LICENSES_LIST, TEAM_ONE)

        log.info("Making request with multiple licenses")
        val response = licensesApi.customerChangeLicensesTeam(
            request = request,
            headerMode = Valid
        )

        assertEquals(OK, response.status)

        assertEquals(
            MULTIPLE_LICENSES_LIST,
            response.requireSuccessBody().licenseIds,
            "Lists of licenses should be equal"
        )
    }

    @Test
    @Description("Test /customer/changeLicensesTeam endpoint with expired value in list of licenses")
    fun changeLicensesTeam_whenLicenseIsExpired_shouldReturnSameExpiredLicenseInResponse() = runTest {

        log.info("Preparing request with expired license $EXPIRED_LICENSE_LIST and team $TEAM_TWO")
        val request = ChangeLicensesTeamRequest(EXPIRED_LICENSE_LIST, TEAM_TWO)

        log.info("Making request with expired license")
        val response = licensesApi.customerChangeLicensesTeam(
            request = request,
            headerMode = Valid
        )

        assertEquals(OK, response.status)

        assertEquals(
            EXPIRED_LICENSE_LIST,
            response.requireSuccessBody().licenseIds,
            "Lists of licenses should be equal"
        )
    }

    @Test
    @Description("Test /customer/changeLicensesTeam endpoint with mixed valid and invalid values in list of licenses")
    fun changeLicensesTeam_whenValidAndInvalidLicenses_shouldReturnOnlyValidLicensesInResponse() = runTest {

        log.info("Preparing request with mixed valid $VALID_LICENSE_LIST and invalid $INVALID_LICENSES_LIST licenses and team $TEAM_TWO")
        val request = ChangeLicensesTeamRequest(VALID_LICENSE_LIST + INVALID_LICENSES_LIST, TEAM_TWO)

        log.info("Making request with mixed licenses")
        val response = licensesApi.customerChangeLicensesTeam(
            request = request,
            headerMode = Valid
        )

        assertEquals(OK, response.status)

        assertEquals(
            VALID_LICENSE_LIST,
            response.requireSuccessBody().licenseIds,
            "There should be only one valid license"
        )
    }

    @AfterAll
    fun cleaningData() = runBlocking {
        TestDataCleaner.restoreValidLicenses()
        TestDataCleaner.restoreExpiredLicense()
        TestDataCleaner.restoreMixedLicense()
    }
}