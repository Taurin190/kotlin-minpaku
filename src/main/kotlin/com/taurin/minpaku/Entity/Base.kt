package com.taurin.minpaku.Entity

import java.util.*
import javax.persistence.MappedSuperclass
import javax.persistence.PrePersist
import javax.persistence.PreUpdate

@MappedSuperclass
open class Base (
    var createdDatetime: Date? = null,
    var updatedDatetime: Date? = null
) {
    @PrePersist
    fun onPrePersist() {
        this.createdDatetime = Date()
        this.updatedDatetime = Date()
    }

    @PreUpdate
    fun onPreUpdate() {
        this.updatedDatetime = Date()
    }
}