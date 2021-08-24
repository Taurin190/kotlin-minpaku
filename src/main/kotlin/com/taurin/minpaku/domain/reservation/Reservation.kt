package com.taurin.minpaku.domain.reservation

class Reservation {
    lateinit var title: Title

    lateinit var start: Start

    var end: End? = null

    var url: Url? = null

    constructor(
        title: Title,
        start: Start,
        end: End?,
        url: Url
    ) {
        this.title = title
        this.start = start
        this.end = end
        this.url = url
    }

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("Reservation" + toJson())
        return sb.toString()
    }

    fun toJson(): String {
        val sb = StringBuilder()
        sb.append("{")
        sb.append("title=$title")
        sb.append(", start=$start")
        if (end!= null) { sb.append(", end=$end") }
        if (url!= null) { sb.append(", url=$url") }
        sb.append("}")
        return sb.toString()
    }
}