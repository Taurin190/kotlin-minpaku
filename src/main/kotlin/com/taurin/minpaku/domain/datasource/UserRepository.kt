package com.taurin.minpaku.domain.datasource

import com.taurin.minpaku.domain.model.user.User

interface UserRepository {
    fun findByUserName(userName: String): User

    fun registerProfile(user: User)
}