package com.taurin.minpaku.domain.reservation

import com.fasterxml.jackson.databind.ObjectMapper

class Reservation(
    var title: Title,
    var start: Start,
    var end: End?,
    var url: Url?
    ) {

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("Reservation [title=$title")
        sb.append(", start=$start")
        if (end != null) { sb.append(", end=$end") }
        if (url != null) { sb.append(", url=$url") }
        sb.append("]")
        return sb.toString()
    }

    fun toJson(): String {
        return ObjectMapper().writeValueAsString(this)
    }
}