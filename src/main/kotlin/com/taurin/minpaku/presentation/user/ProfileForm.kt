package com.taurin.minpaku.presentation.user

import lombok.Data

@Data
class ProfileForm {
    var name: String = ""
    var email: String = ""
    var address: String = ""
    var phone: String = ""
}