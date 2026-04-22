package model.licenses.customerChangeLicensesTeam

import kotlinx.serialization.Serializable

@Serializable
data class ChangeLicensesTeamRequest(
    val licenseIds: List<String>? = null,
    val targetTeamId: Int? = null
)
