package com.taurin.minpaku.Service

import com.taurin.minpaku.Enum.Permission
import com.taurin.minpaku.Repository.UserRepository
import com.taurin.minpaku.Entity.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.User as AuthUser
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
        val user = User(
            null,
            userName,
            passwordEncoder.encode(password),
            Permission.USER
        )
        userRepository.save(user)
    }

    override fun loadUserByUsername(userName: String): UserDetails {
        val user = userRepository.findByUserName(userName)
        val userDetails = AuthUser
            .withUsername(user.userName)
            .password(user.password)
            .roles("USER")
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