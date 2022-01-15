package com.taurin.minpaku.presentation.user

import com.taurin.minpaku.domain.model.user.Profile
import lombok.Data

@Data
class ProfileForm {
    var name: String = ""
    var email: String = ""
    var address: String = ""
    var phone: String = ""

    fun getProfileDomain() : Profile {
        return Profile.create(
                mapOf(
                        "name" to name,
                        "email_address" to email,
                        "phone_number" to phone,
                        "address" to address,
                )
        )
    }
}