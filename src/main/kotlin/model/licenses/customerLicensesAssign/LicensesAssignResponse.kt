package model.licenses.customerLicensesAssign

import kotlinx.serialization.Serializable

@Serializable
data class LicensesAssignResponse(
    val licenseIds: List<String>? = null,
    val code: String? = null,
    val description: String? = null
)
