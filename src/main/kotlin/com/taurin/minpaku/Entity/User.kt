package com.taurin.minpaku.Entity

import javax.persistence.*

@Entity
@Table(name = "user")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val userId: Long? = null,
    @Column(name = "username", nullable = false)
    val userName: String = "",
    @Column(nullable = false)
    val password: String = "",
    @Column(columnDefinition = "int default 0")
    var permissionId: Int = 0,
    @OneToOne(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    var profile: Profile? = null
)