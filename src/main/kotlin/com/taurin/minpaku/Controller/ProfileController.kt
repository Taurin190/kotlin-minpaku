package com.taurin.minpaku.Controller

import com.taurin.minpaku.Exception.DBException
import com.taurin.minpaku.Exception.ProfileNotFound
import com.taurin.minpaku.Form.ProfileForm
import com.taurin.minpaku.Service.ProfileService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Controller
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView
import javax.validation.Valid

@Controller
@RequestMapping("/profile")
class ProfileController {
    @Autowired
    private lateinit var profileService: ProfileService

    private val logger = LoggerFactory.getLogger(ProfileController::class.java)

    @GetMapping("")
    fun profile(
        mav: ModelAndView,
        @AuthenticationPrincipal userDetail: UserDetails
    ): ModelAndView {
        mav.viewName = "profile"
        try {
            val profile = profileService.findByUsername(userDetail.username)
            mav.addObject("profile", profile)
        } catch(e: ProfileNotFound) {
            logger.warn("User: ${userDetail.username} access profile without register")
            mav.viewName = "not_found"
            mav.addObject("error", e.message)
            mav.status = HttpStatus.NOT_FOUND
        } catch(e: Exception) {
            logger.error("Unexpected Exception: ${e.message}")
        }
        return mav
    }

    @GetMapping("/form")
    fun registerForm(mav: ModelAndView): ModelAndView {
        mav.viewName = "profile_form"
        mav.addObject("profileForm", ProfileForm())
        return mav
    }

    @PostMapping("/register")
    fun register(
        @Valid form: ProfileForm,
        bindingResult: BindingResult,
        @ModelAttribute mav: ModelAndView,
        @AuthenticationPrincipal userDetail: UserDetails
    ): ModelAndView {
        mav.viewName = "profile_form"
        if (bindingResult.hasErrors()) {
            return mav
        }
        try {
            logger.info("User: ${userDetail.username} register profile")
            profileService.register(
                userDetail.username,
                form.name,
                form.email,
                form.address,
                form.phone
            )
        } catch (e: DBException) {
            mav.viewName = "profile_form"
            mav.addObject("error", e.message)
        }
        return mav
    }
}