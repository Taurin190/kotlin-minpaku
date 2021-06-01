package com.taurin.minpaku.Presentation.Controller.Api

import com.taurin.minpaku.Presentation.Response.ApiResponse
import com.taurin.minpaku.Presentation.Response.BookResponse
import com.taurin.minpaku.Presentation.Response.ReservationResponse
import com.taurin.minpaku.Service.ReserveService
import com.taurin.minpaku.Util.DateUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.time.ZonedDateTime

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

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    fun register() : ApiResponse {
        return ApiResponse(
            ZonedDateTime.now(),
            HttpStatus.CREATED,
            "正常に登録できました。"
        )
    }
}