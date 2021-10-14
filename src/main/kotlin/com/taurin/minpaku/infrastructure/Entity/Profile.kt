package com.taurin.minpaku.infrastructure.Entity

import com.taurin.minpaku.domain.model.user.EmailAddress
import com.taurin.minpaku.domain.model.user.Name
import com.taurin.minpaku.domain.model.user.PhoneNumber
import com.taurin.minpaku.domain.model.user.UserName
import com.taurin.minpaku.domain.model.user.Profile as ProfileDomain
import javax.persistence.*

@Entity
@Table(name = "profile")
data class Profile(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @OneToOne(cascade = [CascadeType.REMOVE])
    @JoinColumn(name = "user_id", unique = true)
    var user: User? = null,
    @Column(nullable = false)
    var name: String = "",
    @Column(nullable = false)
    var email: String = "",
    @Column(nullable = false)
    var address: String = "",
    @Column(name = "phone_number", nullable = false)
    var phoneNumber: String = ""
) : Base()
{
    fun toDomain(): ProfileDomain {
        return ProfileDomain(
            Name(name),
            EmailAddress(email),
            PhoneNumber(phoneNumber)
        )
    }
}
