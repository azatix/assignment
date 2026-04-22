package config

sealed interface HeaderMode {
    data object Valid : HeaderMode
    data object Missing : HeaderMode
    data class Invalid(
        val apiKeyHeader: String? = null,
        val customerCodeHeader: String? = null
    ) : HeaderMode

    data class Custom(
        val headers: Map<String, String?>
    ) : HeaderMode
}