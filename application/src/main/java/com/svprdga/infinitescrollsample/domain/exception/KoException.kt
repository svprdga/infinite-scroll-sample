package com.svprdga.infinitescrollsample.domain.exception

/**
 * Exception class used when a remote server responds with a KO result.
 */
class KoException(
    private val statusCode: Int,
    private val errors: List<String?>? = listOf()) : Exception() {

    // ****************************************** VARS ***************************************** //

    override val message: String?
        get() = if (errors != null && errors.isNotEmpty()) {
            "[KoException]: Status code: $statusCode, errors: $errors"
        } else {
            "[KoException]: Status code: $statusCode"
        }
}