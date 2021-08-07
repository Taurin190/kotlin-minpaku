package com.taurin.minpaku.presentation.reservation

class ReservationResponse (
    val reservationId: Long?,
    val userId: Long?,
    val userName: String?,
    val bookList: List<BookResponse>
)