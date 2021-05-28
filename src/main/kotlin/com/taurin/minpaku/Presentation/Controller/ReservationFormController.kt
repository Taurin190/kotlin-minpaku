package com.taurin.minpaku.Presentation.Controller

import com.taurin.minpaku.Data.Entity.Profile
import com.taurin.minpaku.Exception.ProfileNotFound
import com.taurin.minpaku.Presentation.Form.ReservationForm
import com.taurin.minpaku.Service.ProfileService
import com.taurin.minpaku.Service.ReserveService
import com.taurin.minpaku.Util.DateUtil
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.ModelAndView

@Controller
@RequestMapping("/reservation")
class ReservationFormController {
    @Autowired
    private lateinit var reserveService: ReserveService

    @Autowired
    private lateinit var profileService: ProfileService

    private val logger = LoggerFactory.getLogger(ReservationFormController::class.java)

    @GetMapping("/form")
    fun form(
        @RequestParam("date") date: String?,
        mav: ModelAndView,
        @AuthenticationPrincipal userDetail: UserDetails
    ): ModelAndView {
        lateinit var profile: Profile
        try {
            profile = profileService.findByUsername(userDetail.username)
        } catch(e: ProfileNotFound) {
            mav.viewName = "not_found"
            mav.addObject("error", e.message)
            return mav
        } catch(e: Exception) {
            logger.error("Unexpected Exception: ${e.message}")
            mav.viewName = "not_found"
            mav.addObject("error", e.message)
            return mav
        }

        val form = ReservationForm()
        val checkInDate = DateUtil.getDateFromStr(date)
        val checkOutDate = DateUtil.getNextDate(checkInDate)

        form.checkInDate = DateUtil.getDateStr(checkInDate)
        form.checkOutDate = DateUtil.getDateStr(checkOutDate)

        mav.viewName = "reservation_form"
        mav.addObject("name", profile.name)
        mav.addObject("reservationForm", form)
        return mav
    }
}