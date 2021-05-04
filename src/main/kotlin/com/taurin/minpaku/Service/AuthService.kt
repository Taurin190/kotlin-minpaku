package com.taurin.minpaku.Service

import com.taurin.minpaku.Enum.Permission
import com.taurin.minpaku.Repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service


@Service
class AuthService(@Autowired val userRepository: UserRepository) : UserDetailsService {
    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    fun login(username: String, password: String) {

    }

    fun register(userName: String, password: String) {
        val user = com.taurin.minpaku.Entity.User(
            null,
            userName,
            passwordEncoder.encode(password),
            Permission.USER
        )
        userRepository.save(user)
    }

    override fun loadUserByUsername(userName: String): UserDetails {
        val user = userRepository.findByUserName(userName)
        val userDetails = User
            .withUsername(user.userName)
            .password(user.password)
            .roles("USER")
            .build()
        return userDetails
    }
}