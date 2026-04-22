package data

object TestConstants {

    object TestTeamIds {
        const val TEAM_ORIGINAL = 2743660
        const val TEAM_ORIGINAL_EXPIRED = 2816306
        const val TEAM_ONE = 2822603
        const val TEAM_TWO = 2822604
    }

    object LicensesIds {
        val ONE_LICENSE_LIST = listOf("BA8G3T5FRF")
        val VALID_LICENSE_LIST = listOf("0M2NDZM6PR")
        val MULTIPLE_LICENSES_LIST = listOf("5EJ0TOSAH8", "NJ5V85RQ77")
        val EXPIRED_LICENSE_LIST = listOf("CGDKYXJNM0")
        val EMPTY_LICENSES_LIST = listOf("")
        const val LICENSE_TO_ASSIGN_ONE = "UMOM0NAN94"
        const val LICENSE_TO_ASSIGN_TWO = "T2VWVFRKH1"
        const val DOUBLE_ASSIGN_LICENSE_ID = "20HO7MMV7W"
    }

    object InvalidData {
        const val INVALID_TOKEN = "177ydhaj91jhdhdhdhaa"
        const val MISMATCH_TOKEN = "6jz8fabys82eutf566400u5k"
        const val INSUFFICIENT_PERMISSIONS_TOKEN = "20i4thqa6tjbd7pqhiccaeprx"
        const val INVALID_CUSTOMER_CODE = "81727172"
        const val INVALID_TEAM = 9819191
        val INVALID_LICENSES_LIST = listOf("71UI910ABA", "IA9181BAH1")
    }

    object ContactData {
        const val DEFAULT_CONTACT_EMAIL = "isaacclarkeengineer@proton.me"
        const val DEFAULT_CONTACT_FIRST_NAME = "Isaac"
        const val DEFAULT_CONTACT_LAST_NAME = "Clarke"
        const val DOUBLE_ASSIGN_CONTACT_EMAIL = "alanwakewriter@proton.me"
        const val DOUBLE_ASSIGN_CONTACT_FIRST_NAME = "Alan"
        const val DOUBLE_ASSIGN_CONTACT_LAST_NAME = "Wake"
    }

    object ProductData {
        const val GO = "GO"
    }
}