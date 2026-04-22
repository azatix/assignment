package licensesAssign

import BaseTest
import api.LicensesApi
import config.HeaderMode.Valid
import data.LicensesAssignRequestFactory
import data.TestConstants.LicensesIds.LICENSE_TO_ASSIGN_TWO
import io.ktor.http.HttpStatusCode.Companion.NoContent
import io.qameta.allure.Description
import io.qameta.allure.Feature
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Tag
import org.slf4j.LoggerFactory
import kotlin.test.Test
import kotlin.test.assertEquals

@Tag("licensesAssignTests")
@Feature("Assign a license to a user. Positive scenarios")
class LicensesAssignPositiveTests : BaseTest() {

    private val log = LoggerFactory.getLogger(javaClass)

    private val licensesApi = LicensesApi()

    @Test
    @Description("Test /customer/licenses/assign with full default request")
    fun assignLicenses_whenDefaultRequest_shouldReturnAnEmpty() = runTest {

        log.info("Preparing valid request")
        val request = LicensesAssignRequestFactory.build(licenseId = LICENSE_TO_ASSIGN_TWO)

        log.info("Making request")
        val response = licensesApi.customerLicensesAssign(
            request = request,
            headerMode = Valid
        )

        assertEquals(NoContent, response.status)

        assertEquals(response.body, null)
    }

    @Test
    @Description("Test /customer/licenses/assign with 'licenseId' and without 'license'")
    fun assignLicenses_whenLicenseIsAbsentLicenseIdIsPresent_shouldReturnAnEmpty() = runTest {

        log.info("Preparing request with 'licenseId' and without 'license'")
        val request = LicensesAssignRequestFactory.withoutLicense()

        log.info("Making request with 'licenseId' and without 'license'")
        val response = licensesApi.customerLicensesAssign(
            request = request,
            headerMode = Valid
        )

        assertEquals(NoContent, response.status)

        assertEquals(response.body, null)
    }

    @Test
    @Description("Test /customer/licenses/assign with 'license' and without 'licenseId'")
    fun assignLicenses_whenLicenseIdIsAbsentAndLicenseIsPresent_shouldReturnAnEmpty() = runTest {

        log.info("Preparing request with 'license' and without 'licenseId'")
        val request = LicensesAssignRequestFactory.withoutLicenseId()

        log.info("Making request with 'license' and without 'licenseId'")
        val response = licensesApi.customerLicensesAssign(
            request = request,
            headerMode = Valid
        )

        assertEquals(NoContent, response.status)

        assertEquals(response.body, null)
    }
}
