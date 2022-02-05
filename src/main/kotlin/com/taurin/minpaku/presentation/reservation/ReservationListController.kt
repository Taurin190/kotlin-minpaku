package com.taurin.minpaku.presentation.reservation

import com.taurin.minpaku.service.ReserveService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView

@Controller
@RequestMapping("/reservation/list")
class ReservationListController {
    @Autowired
    private lateinit var reserveService: ReserveService

    @GetMapping("")
    fun list(mav: ModelAndView): ModelAndView {
        mav.viewName = "reservation/list"
        return mav
    }
}