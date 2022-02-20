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
        val year =  DateUtil.getValidYear(yearStr)
        val month = DateUtil.getValidMonth(monthStr)
        val reservations = reserveService.getReservationsInMonth(
                year,
                month
        )
        if (yearStr != "" && monthStr != "") {
            mav.addObject("date", "${yearStr}年${monthStr}月")
        }
        val prevParam = DateUtil.getPrevMonth(year, month)
        val nextParam = DateUtil.getNextMonth(year, month)
        mav.addObject("prev_year", prevParam[0])
        mav.addObject("prev_month", prevParam[1])
        mav.addObject("next_year", nextParam[0])
        mav.addObject("next_month", nextParam[1])
        mav.addObject("reservations", reservations)
        return mav
    }
}