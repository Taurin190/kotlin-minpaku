package com.taurin.minpaku.domain.reservation

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

class Url(
    @NotBlank(message = "URLを入力してください。")
    val value: String
    )
{
    override fun toString() = value
}