package com.taurin.minpaku.Form

import lombok.Data

@Data
class RegisterForm {
    var username: String = ""
    var password: String = ""
    var passwordMatch: String = ""
    var email: String = ""
}