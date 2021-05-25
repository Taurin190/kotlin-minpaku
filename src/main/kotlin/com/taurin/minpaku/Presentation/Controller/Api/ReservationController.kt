package com.taurin.minpaku.Presentation.Controller.Api

import com.taurin.minpaku.Data.Entity.Reservation
import com.taurin.minpaku.Presentation.Response.ApiResponse
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
    ) : List<Reservation> {
        val year = DateUtil.getValidYear(yearStr)
        val month = DateUtil.getValidMonth(monthStr)
        return reserveService.getReservationsByDuration(year, month)
    }

    @PostMapping("/add")
    fun register() : ApiResponse {
        return ApiResponse(
            ZonedDateTime.now(),
            HttpStatus.CREATED,
            "正常に登録できました。"
        )
    }
}