package com.taurin.minpaku.presentation.user

import com.taurin.minpaku.infrastructure.exception.DBException
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
import javax.validation.Valid

@Controller
@RequestMapping("/")
class RegisterController(@Autowired private val authService: AuthService) {
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
}