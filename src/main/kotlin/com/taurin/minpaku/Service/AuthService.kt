package com.taurin.minpaku.Service

import com.taurin.minpaku.Data.Entity.User
import com.taurin.minpaku.Enum.Permission
import com.taurin.minpaku.Exception.DBException
import com.taurin.minpaku.Data.Repository.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.security.core.userdetails.User as AuthUser

@Service
class AuthService(
    @Autowired val userRepository: UserRepository
) : UserDetailsService {
    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    private val logger = LoggerFactory.getLogger(AuthService::class.java)

    fun register(userName: String, password: String) {
        val user = User(
            null,
            userName,
            passwordEncoder.encode(password),
            Permission.USER
        )
        try {
            userRepository.save(user)
        } catch (e: Exception) {
            logger.warn("Register fail with Unexpected Exception: ${e.message}")
            throw DBException("既にユーザ名が使用されています。")
        }
    }

    override fun loadUserByUsername(userName: String): UserDetails {
        val user = userRepository.findByUserName(userName)
        val userDetails = AuthUser
            .withUsername(user.userName)
            .password(user.password)
            .roles(user.permission.toString())
            .build()
        return userDetails
    }

    fun registerAdminUser(user: User) {
        userRepository.insertIgnore(
            user.userName,
            user.password,
            user.permission.num
        )
    }
}