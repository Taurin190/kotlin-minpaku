package com.taurin.minpaku.Service

import com.taurin.minpaku.Repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService

@Service
class AuthService(@Autowired val userRepository: UserRepository) : UserDetailsService {
    fun login(username: String, password: String) {

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