package com.taurin.minpaku.Entity

import javax.persistence.*

@Entity
@Table(name = "user")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    val userId: Long? = null,
    @Column(name = "username", nullable = false)
    val userName: String = "",
    @Column(nullable = false)
    val password: String = "",
    @Column(name = "permission_id", columnDefinition = "int default 0")
    var permissionId: Int = 0
)