package com.taurin.minpaku.infrastructure.exception


class DBException: Exception {
    constructor(message: String?) : super(message)

    constructor(message: String, exception: Throwable) : super(message, exception)
}