package com.taurin.minpaku.Service

import com.taurin.minpaku.Repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails

@Service
class AuthService(@Autowired val userRepository: UserRepository) {
    fun login(username: String, password: String) {

    }

    fun loadUserByUserName(userName: String): UserDetails {
        val user = userRepository.findByUserName(userName)
        val userDetails = User
            .withUsername(user.userName)
            .password(user.password)
            .roles("USER")
            .build()
        return userDetails
    }
}