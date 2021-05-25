package com.taurin.minpaku.Presentation.Form

import lombok.Data

@Data
class ProfileForm {
    var name: String = ""
    var email: String = ""
    var address: String = ""
    var phone: String = ""
}