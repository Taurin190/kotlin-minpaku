package com.taurin.minpaku.Presentation.Request

import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.security.web.util.matcher.RequestMatcher
import javax.servlet.http.HttpServletRequest

class CsrfRequestMatcher : RequestMatcher {
    override fun matches(request: HttpServletRequest?): Boolean {
        val disabledRequestMatcher = AntPathRequestMatcher("/api/**")
        if (request != null) {
            if ("GET" == request.method) {
                return false
            }
            if (disabledRequestMatcher.matches(request)) {
                return false
            }
        }
        return true
    }
}