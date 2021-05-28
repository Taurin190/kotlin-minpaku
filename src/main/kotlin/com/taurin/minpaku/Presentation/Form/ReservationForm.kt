package com.taurin.minpaku.Presentation.Form

import lombok.Data
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@Data
class ReservationForm(
    var reservationId: Long? = null,
    @NotBlank
    var checkInDate: String? = null,
    @NotBlank
    var checkOutDate: String? = null,
    @Size(min = 1, max = 2)
    var guestNum: Int = 1
)