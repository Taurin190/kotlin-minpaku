package com.taurin.minpaku.Controller

import com.taurin.minpaku.Form.LoginForm
import com.taurin.minpaku.Form.RegisterForm
import com.taurin.minpaku.Service.AuthService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView

@Controller
@RequestMapping("/")
class AuthController(@Autowired private val authService: AuthService) {
    @GetMapping("/login")
    fun loginForm(mav: ModelAndView): ModelAndView {
        mav.viewName = "login"
        mav.addObject("loginForm", LoginForm())
        return mav
    }

    @PostMapping("/login")
    fun login(@ModelAttribute form: LoginForm, mav: ModelAndView): ModelAndView {
        print(form.username)
        print(form.password)
        mav.viewName = "login"
        return mav
    }

    @GetMapping("/login/success")
    fun loginSuccess(mav: ModelAndView): ModelAndView {
        mav.viewName = "login"
        return mav
    }

    @GetMapping("/logout")
    fun logout(mav: ModelAndView): ModelAndView {
        mav.viewName = "home"
        return mav
    }

    @GetMapping("/register")
    fun registerForm(mav: ModelAndView): ModelAndView {
        mav.viewName = "register"
        mav.addObject("registerForm", RegisterForm())
        return mav
    }

    @PostMapping("/register")
    fun register(@ModelAttribute form: RegisterForm, mav: ModelAndView): ModelAndView {
        mav.viewName = "register"
        authService.register(form.username, form.password)
        return mav
    }

    @GetMapping("/password/reset")
    fun passwordReset(mav: ModelAndView): ModelAndView {
        mav.viewName = "home"
        return mav
    }
}