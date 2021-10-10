package com.taurin.minpaku.presentation.reservation

import com.taurin.minpaku.service.ReserveService
import com.taurin.minpaku.util.DateUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import java.nio.charset.StandardCharsets

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
        val headers = HttpHeaders()
        headers.contentType = MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)
        return ReservationsResponse(reserveService
            .getReservationsInMonth(
                DateUtil.getValidYear(yearStr),
                DateUtil.getValidMonth(monthStr)
            )).json
    }
}