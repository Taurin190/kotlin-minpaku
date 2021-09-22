package com.taurin.minpaku.domain.model.reservation

import javax.validation.constraints.NotBlank

class Url(
    @NotBlank(message = "URLを入力してください。")
    val value: String
) : java.io.Serializable {
    override fun toString() = value
}