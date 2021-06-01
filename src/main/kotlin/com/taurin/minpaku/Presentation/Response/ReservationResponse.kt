package com.taurin.minpaku.Presentation.Response

class ReservationResponse (
    val reservationId: Long?,
    val userId: Long?,
    val userName: String?,
    val bookList: List<BookResponse>
)