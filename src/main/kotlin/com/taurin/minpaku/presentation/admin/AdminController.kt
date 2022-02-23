package com.taurin.minpaku.presentation.admin

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView

@Controller
@RequestMapping("/admin")
class AdminController {

    @GetMapping("")
    fun index(mav: ModelAndView): ModelAndView {
        mav.viewName = "admin"
        return mav
    }
}