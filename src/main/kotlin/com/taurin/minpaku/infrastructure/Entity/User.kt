package com.taurin.minpaku.infrastructure.Entity

import com.taurin.minpaku.domain.model.user.UserName
import javax.persistence.*
import com.taurin.minpaku.domain.type.Permission
import com.taurin.minpaku.domain.model.user.User as UserDomain

@Entity
@Table(name = "user", indexes = arrayOf(Index(name = "username_index", columnList = "username", unique = true)))
data class User (
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
    var profile: Profile? = null,
    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    var reservations: List<Reservation>? = null
) : Base()
{
    fun toDomain(): UserDomain {
        return UserDomain(
            UserName(userName),
            profile?.toDomain(),
            permission
        )
    }
    companion object {
        fun fromDomain(userDomain: UserDomain, userEntity: User? = null): User {
            return User(
                    userEntity?.userId,
                    userDomain.userName.toString(),
                    userEntity?.password ?: "",
                    userDomain.permission,
                    if (userDomain.profile != null) Profile.fromDomain(userDomain.profile) else null,
                    userEntity?.reservations
            )
        }
    }
}