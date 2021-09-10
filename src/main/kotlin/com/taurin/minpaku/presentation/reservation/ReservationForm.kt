package com.taurin.minpaku.presentation.reservation

import lombok.Data
import org.hibernate.validator.constraints.Range
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

@Data
class ReservationForm {
    @NotBlank
    @Size(min = 10, max = 10)
    @Pattern(
        regexp = "^20[0-9]{2}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])",
        message = "日付はYYYY-MM-DDの形式で2000年以降2100年までの日付が指定可能です。"
    )
    var checkInDate: String = ""

    @NotBlank
    @Size(min = 3, max = 5)
    @Pattern(
        regexp = "^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]",
        message = "時間はHH:mmの形式で指定可能です。"
    )
    var checkInTime: String = "17:00"

    @NotBlank
    @Size(min = 10, max = 10)
    @Pattern(
        regexp = "^20[0-9]{2}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])",
        message = "日付はYYYY-MM-DDの形式で2000年以降2100年までの日付が指定可能です。"
    )
    var checkOutDate: String = ""

    @NotBlank
    @Size(min = 3, max = 5)
    @Pattern(
        regexp = "^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]",
        message = "時間はHH:mmの形式で指定可能です。"
    )
    var checkOutTime: String = "09:00"

    @Range(
        min = 1,
        max = 2,
        message = "宿泊人数は１人もしくは２人までの指定が可能です。"
    )
    var guestNum: Int = 1
}