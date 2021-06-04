package com.taurin.minpaku.Presentation.Controller.Profile

import com.taurin.minpaku.Exception.DBException
import com.taurin.minpaku.Exception.ProfileNotFound
import com.taurin.minpaku.Presentation.Form.ProfileForm
import com.taurin.minpaku.Service.ProfileService
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
class CreateController {
    @Autowired
    private lateinit var profileService: ProfileService

    private val logger = LoggerFactory.getLogger(CreateController::class.java)

    @GetMapping("/form")
    fun registerForm(
        mav: ModelAndView,
        @AuthenticationPrincipal userDetail: UserDetails
    ): ModelAndView {
        try {
            profileService.findByUsername(userDetail.username)
            mav.viewName = "redirect:/profile"
            return mav
        } catch (e: ProfileNotFound) {
            logger.info("Profile Not Found: ${e.message}")
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
            profileService.register(
                userDetail.username,
                form.name,
                form.email,
                form.address,
                form.phone
            )
            mav.viewName = "profile/complete"
        } catch (e: DBException) {
            mav.viewName = "profile/error"
            mav.addObject("error", e.message)
        }
        return mav
    }
}