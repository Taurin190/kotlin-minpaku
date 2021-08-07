package com.taurin.minpaku.presentation.user

import com.taurin.minpaku.service.ProfileService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView

@Controller
@RequestMapping("/profile")
class ProfileController {
    @Autowired
    private lateinit var profileService: ProfileService

    private val logger = LoggerFactory.getLogger(ProfileCreateController::class.java)

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
}