package com.taurin.minpaku.presentation.user

import lombok.Data
import javax.validation.constraints.NotBlank

@Data
class LoginForm {
    @NotBlank
    var username: String = ""
    @NotBlank
    var password: String = ""
}