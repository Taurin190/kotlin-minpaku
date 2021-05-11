package com.taurin.minpaku.Form

import lombok.Data
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

@Data
class RegisterForm {
    @NotBlank
    @Size(min = 5, max = 20)
    @Pattern(
        regexp = "(?=.{5,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$",
        message = "英数字および._のみ有効です。先頭は英数字を設定してください。"
    )
    var username: String = ""
    @NotBlank
    @Size(min = 5, max = 255)
    @Pattern(regexp = "[a-zA-Z0-9]*", message = "英数字のみ有効です。")
    var password: String = ""
    @NotBlank
    @Size(min = 5, max = 255)
    @Pattern(regexp = "[a-zA-Z0-9]*", message = "英数字のみ有効です。")
    var passwordMatch: String = ""
}