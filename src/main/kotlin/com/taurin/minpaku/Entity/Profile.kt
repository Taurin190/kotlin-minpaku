package com.taurin.minpaku.Entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "profile")
data class Profile(
    @Id
    @Column(name = "user_id")
    var userId: Long,
    @Column(nullable = false)
    var name: String,
    @Column(nullable = false)
    var email: String,
    @Column(nullable = false)
    var address: String,
    @Column(name = "phone_number", nullable = false)
    var phoneNumber: String
)
