package utils

import api.LicensesApi
import config.HeaderMode.Valid
import data.TestConstants.LicensesIds.EXPIRED_LICENSE_LIST
import data.TestConstants.LicensesIds.MULTIPLE_LICENSES_LIST
import data.TestConstants.LicensesIds.ONE_LICENSE_LIST
import data.TestConstants.LicensesIds.VALID_LICENSE_LIST
import data.TestConstants.TestTeamIds.TEAM_ORIGINAL
import data.TestConstants.TestTeamIds.TEAM_ORIGINAL_EXPIRED
import model.licenses.customerChangeLicensesTeam.ChangeLicensesTeamRequest

object TestDataCleaner {

    private val licensesApi = LicensesApi()

    suspend fun restoreValidLicenses() {

        val requestToOriginalTeam = ChangeLicensesTeamRequest(
            ONE_LICENSE_LIST + MULTIPLE_LICENSES_LIST,
            TEAM_ORIGINAL
        )

        licensesApi.customerChangeLicensesTeam(
            request = requestToOriginalTeam,
            headerMode = Valid
        )
    }

    suspend fun restoreExpiredLicense() {

        val requestToOriginalExpiredTeam = ChangeLicensesTeamRequest(
            EXPIRED_LICENSE_LIST,
            TEAM_ORIGINAL_EXPIRED
        )

        licensesApi.customerChangeLicensesTeam(
            request = requestToOriginalExpiredTeam,
            headerMode = Valid
        )
    }

    suspend fun restoreMixedLicense() {

        val requestToOriginalExpiredTeam = ChangeLicensesTeamRequest(
            VALID_LICENSE_LIST,
            TEAM_ORIGINAL
        )

        licensesApi.customerChangeLicensesTeam(
            request = requestToOriginalExpiredTeam,
            headerMode = Valid
        )
    }
}
