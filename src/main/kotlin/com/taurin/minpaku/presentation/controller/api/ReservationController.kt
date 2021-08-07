package com.taurin.minpaku.presentation.controller.api

import com.taurin.minpaku.presentation.response.BookResponse
import com.taurin.minpaku.presentation.response.ReservationResponse
import com.taurin.minpaku.service.ReserveService
import com.taurin.minpaku.util.DateUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/reservation")
class ReservationController {
    @Autowired
    private lateinit var reserveService: ReserveService

    @GetMapping("/list")
    fun all(
        @RequestParam(value = "year", defaultValue = "") yearStr: String,
        @RequestParam(value = "month", defaultValue = "") monthStr: String
    ) : List<ReservationResponse> {
        val year = DateUtil.getValidYear(yearStr)
        val month = DateUtil.getValidMonth(monthStr)
        val reservationList = reserveService.getReservationsByDuration(year, month)
        var responseList = mutableListOf<ReservationResponse>()

        reservationList.forEach {
            var bookList = mutableListOf<BookResponse>()
            it.books.forEach { book ->
                bookList.add(BookResponse(book.bookId, book.guestNum, book.stayDate))
            }
            responseList.add(
                ReservationResponse(
                    it.reservationId,
                    it.user?.userId,
                    it.user?.userName,
                    bookList
                )
            )
        }
        return responseList
    }
}