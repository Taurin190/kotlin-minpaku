package com.taurin.minpaku.Controller

import com.taurin.minpaku.Service.AuthService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView

@Controller
@RequestMapping("/")
class AuthController(@Autowired private val authService: AuthService) {
    @GetMapping("/login")
    fun login(mav: ModelAndView): ModelAndView {
        mav.viewName = "home"
        return mav
    }

    @GetMapping("/logout")
    fun logout(mav: ModelAndView): ModelAndView {
        mav.viewName = "home"
        return mav
    }

    @GetMapping("/register")
    fun register(mav: ModelAndView): ModelAndView {
        mav.viewName = "home"
        return mav
    }

    @GetMapping("/password/reset")
    fun passwordReset(mav: ModelAndView): ModelAndView {
        mav.viewName = "home"
        return mav
    }
}