package com.taurin.minpaku.presentation.reservation

import com.taurin.minpaku.infrastructure.exception.DBException
import com.taurin.minpaku.domain.model.user.User as UserDomain
import com.taurin.minpaku.service.ProfileService
import com.taurin.minpaku.service.ReserveService
import com.taurin.minpaku.service.UserService
import com.taurin.minpaku.util.DateUtil
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Controller
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView
import javax.servlet.http.HttpSession


@Controller
@RequestMapping("/reservation")
@SessionAttributes(types = [ReservationForm::class])
class ReservationFormController {
    @Autowired
    private lateinit var reserveService: ReserveService

    @Autowired
    private lateinit var profileService: ProfileService

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var session: HttpSession

    private val logger = LoggerFactory.getLogger(ReservationFormController::class.java)

    @GetMapping("/form")
    fun form(
        @RequestParam("date") date: String?,
        mav: ModelAndView,
        @AuthenticationPrincipal userDetail: UserDetails
    ): ModelAndView {
        val user = userService.getByUserName(userDetail.username)
        if (user.profile == null) {
            mav.viewName = "not_found"
            mav.addObject("error", "予約に必要な個人情報が登録されていません。")
            return mav
        }

        session.setAttribute("name", user.profile.name)
        session.setAttribute("user", user)

        val form = ReservationForm()
        val checkInDate = DateUtil.getDateFromStr(date)
        val checkOutDate = DateUtil.getNextDate(checkInDate)

        form.checkInDate = DateUtil.getDateStr(checkInDate)
        form.checkOutDate = DateUtil.getDateStr(checkOutDate)

        mav.viewName = "reservation/form"
        mav.addObject("name", user.profile.name)
        mav.addObject("reservationForm", form)
        return mav
    }

    @PostMapping("/confirm")
    fun confirm(
        @Validated form: ReservationForm,
        bindingResult: BindingResult,
        @ModelAttribute mav: ModelAndView
    ): ModelAndView {
        if (bindingResult.hasErrors()) {
            mav.viewName = "reservation/form"
            return mav
        }
        mav.viewName = "reservation/confirm"
        mav.addObject("reservation", form)
        mav.addObject("name", session.getAttribute("name"))
        return mav
    }

    @PostMapping("/complete")
    fun complete(
        @ModelAttribute form: ReservationForm,
        @ModelAttribute mav: ModelAndView
    ): ModelAndView {
        val user = session.getAttribute("user") as UserDomain
        val reservationDomain = form.getReservationWithUser(user)

        try {
            reserveService.register(reservationDomain)
            mav.viewName = "reservation/complete"
        } catch (e: DBException) {
            logger.warn(e.message)
            mav.status = HttpStatus.CONFLICT
            mav.viewName = "reservation/error"
        } catch (e: Exception) {
            logger.error("Unexpected Reservation Error: $e.message")
            mav.status = HttpStatus.CONFLICT
            mav.viewName = "reservation/error"
        }
        return mav
    }
}