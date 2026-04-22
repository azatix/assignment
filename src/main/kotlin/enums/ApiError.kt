package enums

enum class ApiError(val code: String) {
    INVALID_CONTACT_EMAIL("INVALID_CONTACT_EMAIL"),
    INVALID_TOKEN_ERROR("INVALID_TOKEN"),
    INSUFFICIENT_PERMISSIONS("INSUFFICIENT_PERMISSIONS"),
    LICENSE_NOT_FOUND("LICENSE_NOT_FOUND"),
    LICENSE_IS_NOT_AVAILABLE_TO_ASSIGN("LICENSE_IS_NOT_AVAILABLE_TO_ASSIGN"),
    MISSING_TOKEN_HEADER_ERROR("MISSING_TOKEN_HEADER"),
    TEAM_NOT_FOUND("TEAM_NOT_FOUND"),
    TEAM_MISMATCH("TEAM_MISMATCH");

    companion object {
        private val map = entries.associateBy(ApiError::code)

        fun from(code: String): ApiError =
            map[code] ?: error("Unknown ErrorCode: $code")
    }
}