package com.taurin.minpaku.Entity

import javax.persistence.*
import com.taurin.minpaku.Enum.Permission

@Entity
@Table(name = "user", indexes = arrayOf(Index(name = "username_index", columnList = "username", unique = true)))
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val userId: Long? = null,
    @Column(name = "username", nullable = false, unique=true)
    val userName: String = "",
    @Column(nullable = false)
    val password: String = "",
    @Column(columnDefinition = "int default 0")
    var permission: Permission = Permission.USER,
    @OneToOne(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    var profile: Profile? = null
)