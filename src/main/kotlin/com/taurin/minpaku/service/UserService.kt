package com.taurin.minpaku.service

import com.taurin.minpaku.domain.model.user.Profile
import com.taurin.minpaku.domain.model.user.User
import com.taurin.minpaku.infrastructure.datasource.UserDataSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService {
    @Autowired
    private lateinit var userDataSource: UserDataSource

    @Transactional
    fun registerProfile(username: String, profile: Profile) {
        userDataSource.registerProfile(username, profile)
    }

    fun getByUserName(userName: String): User {
        return userDataSource.findByUserName(userName)
    }

    fun confirmProfileByUserName(userName: String): Boolean {
        val user = getByUserName(userName)
        return user.hasProfile()
    }
}