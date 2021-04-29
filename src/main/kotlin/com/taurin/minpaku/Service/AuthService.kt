package com.taurin.minpaku.Service

import com.taurin.minpaku.Dto.UserDto
import com.taurin.minpaku.Repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AuthService(@Autowired userRepository: UserRepository) {
    fun login(username: String, password: String) {

    }

    fun loadUserByUserName(userName: String): UserDto {
        return UserDto()
    }
}