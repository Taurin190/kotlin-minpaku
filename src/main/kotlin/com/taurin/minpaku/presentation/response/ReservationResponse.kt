package com.taurin.minpaku.presentation.response

class ReservationResponse (
    val reservationId: Long?,
    val userId: Long?,
    val userName: String?,
    val bookList: List<BookResponse>
)