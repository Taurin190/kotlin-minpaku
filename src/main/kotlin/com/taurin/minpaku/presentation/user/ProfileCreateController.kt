package com.taurin.minpaku.presentation.user

import com.taurin.minpaku.infrastructure.exception.DBException
import com.taurin.minpaku.service.UserService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Controller
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView
import javax.validation.Valid

@Controller
@RequestMapping("/profile")
@SessionAttributes(types = [ProfileForm::class])
class ProfileCreateController {
    @Autowired
    private lateinit var userService: UserService

    private val logger = LoggerFactory.getLogger(ProfileCreateController::class.java)

    @GetMapping("/form")
    fun registerForm(
        mav: ModelAndView,
        @AuthenticationPrincipal userDetail: UserDetails
    ): ModelAndView {
        if (userService.confirmProfileByUserName(userDetail.username)) {
            mav.viewName = "redirect:/profile"
            return mav
        }
        mav.viewName = "profile/form"
        mav.addObject("profileForm", ProfileForm())
        return mav
    }

    @PostMapping("/confirm")
    fun confirm(
        @ModelAttribute form: ProfileForm,
        bindingResult: BindingResult,
        @ModelAttribute mav: ModelAndView
    ): ModelAndView {
        if (bindingResult.hasErrors()) {
            mav.viewName = "profile/form"
            return mav
        }
        mav.viewName = "profile/confirm"
        mav.addObject("profile", form)
        return mav
    }

    @PostMapping("/complete")
    fun complete(
        @Valid form: ProfileForm,
        bindingResult: BindingResult,
        @ModelAttribute mav: ModelAndView,
        @AuthenticationPrincipal userDetail: UserDetails
    ): ModelAndView {
        try {
            logger.info("User: ${userDetail.username} register profile")
            val profile = form.getProfileDomain()
            userService.registerProfile(userDetail.username, profile)
            mav.viewName = "profile/complete"
        } catch (e: DBException) {
            mav.viewName = "profile/error"
            mav.addObject("error", e.message)
        }
        catch (e: Exception) {
            mav.viewName = "profile/form"
            mav.addObject("error", e.message)
            logger.warn( e.message)
        }
        return mav
    }
}