package com.svprdga.infinitescrollsample.domain.exception

private const val DEFAULT_DESCRIPTION = "No error message."
private const val DEFAULT_RESULT_CODE = 0

/**
 * Exception class used when a remote server responds with a KO result.
 */
class KoException : Exception {

    // ****************************************** VARS ***************************************** //

    var httpCode: Int = 0
        private set
    var resultCode: Int = 0
        private set

    private var _description: String? = null
    private val description: String
        get() {
            if (_description == null) {
                _description = DEFAULT_DESCRIPTION
            }
            return _description!!
        }

    override val message: String?
        get() = if (resultCode != DEFAULT_RESULT_CODE && _description !== DEFAULT_DESCRIPTION) {
            "[KoException]: Result code: $resultCode, error description: $_description."
        } else {
            "[KoException]: $description"
        }

    // ************************************** CONSTRUCTORS ************************************* //

    /**
     * Creates a new instance of [KoException] class with the corresponding error code and
     * message.
     *
     * @param httpCode Http status code.
     * @param resultCode Result code.
     * @param message Error message.
     */
    constructor(httpCode: Int, resultCode: Int, message: String) {
        this.httpCode = httpCode
        this.resultCode = resultCode
        _description = message
    }

    /**
     * Creates a new instance of [KoException] class with the corresponding error code and
     * message.
     *
     * @param httpCode Http status code.
     */
    constructor(httpCode: Int) {
        this.httpCode = httpCode
        resultCode = DEFAULT_RESULT_CODE
        _description = DEFAULT_DESCRIPTION
    }
}