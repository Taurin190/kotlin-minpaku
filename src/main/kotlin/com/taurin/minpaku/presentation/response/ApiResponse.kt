package com.taurin.minpaku.presentation.response

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.http.HttpStatus
import java.time.ZonedDateTime

class ApiResponse (
    @JsonProperty("timestamp")
    val timestamp: ZonedDateTime,
    @JsonProperty("status")
    val status: HttpStatus,
    @JsonProperty("message")
    val message: String,
)
