package com.taurin.minpaku.domain.reservation

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

class Start(
    @NotBlank
    @Size(min = 10, max = 10)
    @Pattern(
        regexp = "^20[1-9]{2}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])",
        message = "日付はYYYY-MM-DDの形式で2000年以降2100年までの日付が指定可能です。"
    )
    val value: String
    ) {
    override fun toString() = value
}