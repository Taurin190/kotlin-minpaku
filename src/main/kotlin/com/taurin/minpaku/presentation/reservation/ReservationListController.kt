package com.taurin.minpaku.presentation.reservation

import com.taurin.minpaku.service.ReserveService
import com.taurin.minpaku.util.DateUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.ModelAndView

@Controller
@RequestMapping("/reservation/list")
class ReservationListController {
    @Autowired
    private lateinit var reserveService: ReserveService

    @GetMapping("")
    fun list(
            @RequestParam(value = "year", defaultValue = "") yearStr: String,
            @RequestParam(value = "month", defaultValue = "") monthStr: String,
            mav: ModelAndView
    ): ModelAndView {
        mav.viewName = "reservation/list"
        val reservations = reserveService.getReservationsInMonth(
                DateUtil.getValidYear(yearStr),
                DateUtil.getValidMonth(monthStr)
        )
        return mav
    }
}