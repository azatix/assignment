package model.licenses.customerLicensesAssign

import kotlinx.serialization.Serializable

@Serializable
data class LicensesAssignRequest(
    val contact: Contact,
    val includeOfflineActivationCode: Boolean,
    val license: License? = null,
    val licenseId: String? = null,
    val sendEmail: Boolean = false
)

@Serializable
data class Contact(
    val email: String,
    val firstName: String,
    val lastName: String
)

@Serializable
data class License(
    val productCode: String,
    val team: Int
)