package com.taurin.minpaku.presentation.user

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView
import javax.servlet.http.HttpSession

@Controller
@RequestMapping("/")
class LoginController() {
    @Autowired
    private lateinit var session: HttpSession

    @GetMapping("/login")
    fun loginForm(mav: ModelAndView): ModelAndView {
        val errorMessage: String? = session.getAttribute("error") as? String
        if (errorMessage != null) {
            mav.addObject("error", errorMessage)
            session.removeAttribute("error")
        }
        mav.viewName = "login"
        mav.addObject("loginForm", LoginForm())
        return mav
    }

    @GetMapping("/password/reset")
    fun passwordReset(mav: ModelAndView): ModelAndView {
        mav.viewName = "home"
        return mav
    }
}