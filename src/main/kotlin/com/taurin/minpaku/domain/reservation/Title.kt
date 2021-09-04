package com.taurin.minpaku.domain.reservation

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

class Title(
    @NotBlank(message = "タイトルを入力してください。")
    @Size(max = 10, message = "{max}文字以内で入力してください。")
    val value: String
) {
    override fun toString() = value
}