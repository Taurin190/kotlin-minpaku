package com.taurin.minpaku.presentation.reservation

import com.taurin.minpaku.service.ReserveService
import com.taurin.minpaku.util.DateUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/reservation")
class ReservationAPIController {
    @Autowired
    private lateinit var reserveService: ReserveService

    @GetMapping("/list")
    fun all(
        @RequestParam(value = "year", defaultValue = "") yearStr: String,
        @RequestParam(value = "month", defaultValue = "") monthStr: String
    ) : String {
        return reserveService
            .getReservationsInMonth(
                DateUtil.getValidYear(yearStr),
                DateUtil.getValidMonth(monthStr)
            )
            .toJson()
    }
}