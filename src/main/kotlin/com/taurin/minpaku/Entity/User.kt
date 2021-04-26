package com.taurin.minpaku.Entity

import lombok.Getter
import org.springframework.context.annotation.Primary
import javax.persistence.*

@Entity
@Table(name = "user")
data class User (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    @Getter
    val userId: Long,
    @Column(nullable = false)
    @Getter
    val password: String,
    @Column(name = "permission_id", columnDefinition = "int default 0")
    @Getter
    var permissionId: Int = 0
)