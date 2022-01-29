package com.taurin.minpaku.presentation.reservation

import com.taurin.minpaku.service.ReserveService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView
import java.util.*

@Controller
@RequestMapping("/")
class HomeController {
    @Autowired
    private lateinit var reserveService: ReserveService

    @GetMapping("/")
    fun index(mav: ModelAndView): ModelAndView {
        val cal = Calendar.getInstance()
        val reservations = reserveService.getReservationsInMonth(
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH) + 1
        )
        mav.viewName = "home"
        mav.addObject("reservations", reservations)
        return mav
    }
}