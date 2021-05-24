package com.taurin.minpaku.Controller

import com.taurin.minpaku.Entity.Reservation
import com.taurin.minpaku.Service.ReserveService
import com.taurin.minpaku.Util.DateUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/reservation")
class ReservationApiController {
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
}