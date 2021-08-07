package com.taurin.minpaku

import org.slf4j.LoggerFactory
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthenticationFailureHandler: AuthenticationFailureHandler {
    private val logger = LoggerFactory.getLogger(AuthenticationFailureHandler::class.java)
    override fun onAuthenticationFailure(
        request: HttpServletRequest,
        response: HttpServletResponse,
        exception: AuthenticationException?
    ) {
        request.session.setAttribute("error", "ユーザ名またはパスワードが正しくありません")
        val username = request.getParameter("username")
        logger.warn("$username : ログイン失敗")
        // ログイン画面にリダイレクト
        response.sendRedirect(request.contextPath + "/login")
    }
}