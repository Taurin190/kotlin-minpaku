package com.taurin.minpaku

import com.taurin.minpaku.Exception.AuthenticationFailureHandler
import com.taurin.minpaku.Service.AuthService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

@Configuration
@EnableWebSecurity
class SecurityConfig : WebSecurityConfigurerAdapter() {
    override fun configure(web: WebSecurity) {
        web.ignoring().antMatchers(
            "/**/favicon.ico",
            "/images/**",
            "/css/**",
            "/js/**"
        )
    }

    override fun configure(http: HttpSecurity) {
        http.authorizeRequests()
            .antMatchers("/", "/login", "/logout", "/register").permitAll()
            .anyRequest().authenticated()

        http.formLogin()
            .loginProcessingUrl("/login")   // 認証処理のパス
            .loginPage("/login")            // ログインフォームのパス
            .failureHandler(AuthenticationFailureHandler())       // 認証失敗時に呼ばれるハンドラクラス
            .defaultSuccessUrl("/")     // 認証成功時の遷移先
            .usernameParameter("username").passwordParameter("password")  // ユーザー名、パスワードのパラメータ名
            .and()

        // ログアウト
        http.logout()
            .logoutRequestMatcher(AntPathRequestMatcher("/logout**"))
            .logoutSuccessUrl("/")
    }

    @Configuration
    open class AuthenticationConfiguration: GlobalAuthenticationConfigurerAdapter() {
        @Autowired
        lateinit var authService : AuthService

        override fun init( auth : AuthenticationManagerBuilder) {
            // 認証するユーザーの設定
            auth.userDetailsService(authService)
        }
    }
}