package com.taurin.minpaku.domain.exception


class InvalidParameterException: Exception {
    constructor(message: String?) : super(message)

    constructor(message: String, exception: Throwable) : super(message, exception)
}