package model.licenses.customerChangeLicensesTeam

import kotlinx.serialization.Serializable

@Serializable
data class ChangeLicensesTeamResponse(
    val licenseIds: List<String>? = null,
    val code: String? = null,
    val description: String? = null
)