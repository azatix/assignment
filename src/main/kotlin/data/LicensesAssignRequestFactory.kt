package data

import data.TestConstants.ContactData.DEFAULT_CONTACT_EMAIL
import data.TestConstants.ContactData.DEFAULT_CONTACT_FIRST_NAME
import data.TestConstants.ContactData.DEFAULT_CONTACT_LAST_NAME
import data.TestConstants.ContactData.DOUBLE_ASSIGN_CONTACT_EMAIL
import data.TestConstants.ContactData.DOUBLE_ASSIGN_CONTACT_FIRST_NAME
import data.TestConstants.ContactData.DOUBLE_ASSIGN_CONTACT_LAST_NAME
import data.TestConstants.InvalidData.INVALID_LICENSES_LIST
import data.TestConstants.LicensesIds.DOUBLE_ASSIGN_LICENSE_ID
import data.TestConstants.LicensesIds.EXPIRED_LICENSE_LIST
import data.TestConstants.LicensesIds.LICENSE_TO_ASSIGN_ONE
import data.TestConstants.ProductData.GO
import data.TestConstants.TestTeamIds.TEAM_ORIGINAL
import data.TestConstants.TestTeamIds.TEAM_TWO
import model.licenses.customerLicensesAssign.Contact
import model.licenses.customerLicensesAssign.License
import model.licenses.customerLicensesAssign.LicensesAssignRequest

object LicensesAssignRequestFactory {

    fun build(
        email: String = DEFAULT_CONTACT_EMAIL,
        firstName: String = DEFAULT_CONTACT_FIRST_NAME,
        lastName: String = DEFAULT_CONTACT_LAST_NAME,
        includeOfflineActivationCode: Boolean = true,
        productCode: String = GO,
        team: Int = TEAM_ORIGINAL,
        license: License? = License(
            productCode = productCode,
            team = team
        ),
        licenseId: String? = LICENSE_TO_ASSIGN_ONE,
        sendEmail: Boolean = false
    ): LicensesAssignRequest {
        return LicensesAssignRequest(
            contact = Contact(
                email = email,
                firstName = firstName,
                lastName = lastName
            ),
            includeOfflineActivationCode = includeOfflineActivationCode,
            license = license,
            licenseId = licenseId,
            sendEmail = sendEmail
        )
    }

    fun buildRepeated(
        email: String = DOUBLE_ASSIGN_CONTACT_EMAIL,
        firstName: String = DOUBLE_ASSIGN_CONTACT_FIRST_NAME,
        lastName: String = DOUBLE_ASSIGN_CONTACT_LAST_NAME,
        includeOfflineActivationCode: Boolean = true,
        productCode: String = GO,
        team: Int = TEAM_TWO,
        licenseId: String = DOUBLE_ASSIGN_LICENSE_ID,
        sendEmail: Boolean = false
    ): LicensesAssignRequest {
        return LicensesAssignRequest(
            contact = Contact(
                email = email,
                firstName = firstName,
                lastName = lastName
            ),
            includeOfflineActivationCode = includeOfflineActivationCode,
            license = License(
                productCode = productCode,
                team = team
            ),
            licenseId = licenseId,
            sendEmail = sendEmail
        )
    }

    fun withInvalidEmail(): LicensesAssignRequest =
        build(email = "invalidEmail")

    fun withoutLicense(): LicensesAssignRequest =
        build(license = null)

    fun withoutLicenseId(): LicensesAssignRequest =
        build(licenseId = null)

    fun withEmptyLicenseId(): LicensesAssignRequest =
        build(licenseId = "")

    fun withExpiredLicenseId(): LicensesAssignRequest =
        build(licenseId = EXPIRED_LICENSE_LIST[0])

    fun withInvalidLicenseId(): LicensesAssignRequest =
        build(licenseId = INVALID_LICENSES_LIST[0])

    fun withOfflineActivationDisabled(): LicensesAssignRequest =
        build(includeOfflineActivationCode = false)
}