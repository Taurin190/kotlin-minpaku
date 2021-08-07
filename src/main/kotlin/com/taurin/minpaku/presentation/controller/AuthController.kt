package com.taurin.minpaku.presentation.controller

import com.taurin.minpaku.exception.DBException
import com.taurin.minpaku.presentation.form.LoginForm
import com.taurin.minpaku.presentation.form.RegisterForm
import com.taurin.minpaku.service.AuthService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView
import javax.servlet.http.HttpSession
import javax.validation.Valid

@Controller
@RequestMapping("/")
class AuthController(@Autowired private val authService: AuthService) {
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

    @GetMapping("/register")
    fun registerForm(mav: ModelAndView): ModelAndView {
        mav.viewName = "register"
        mav.addObject("registerForm", RegisterForm())
        return mav
    }

    @PostMapping("/register")
    fun register(
        @Valid form: RegisterForm,
        bindingResult: BindingResult,
        @ModelAttribute mav: ModelAndView): ModelAndView {
        mav.viewName = "register"
        if (!form.password.equals(form.passwordMatch)) {
            val fieldError = FieldError(
                bindingResult.objectName,
                "password",
                "パスワードが一致しません。"
            )
            bindingResult.addError(fieldError)
        }
        if (bindingResult.hasErrors()) {
            return mav
        }
        try {
            authService.register(form.username, form.password)
        } catch (e: DBException) {
            mav.addObject("error", e.message)
        }
        mav.viewName = "register/complete"
        return mav
    }

    @GetMapping("/password/reset")
    fun passwordReset(mav: ModelAndView): ModelAndView {
        mav.viewName = "home"
        return mav
    }
}