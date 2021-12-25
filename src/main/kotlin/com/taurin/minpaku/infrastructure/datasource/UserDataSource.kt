package com.taurin.minpaku.infrastructure.datasource

import com.taurin.minpaku.domain.model.user.User
import com.taurin.minpaku.infrastructure.Repository.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository

@Repository
class UserDataSource(
        val userRepository: UserRepository
) : com.taurin.minpaku.domain.datasource.UserRepository {
    private val logger = LoggerFactory.getLogger(UserDataSource::class.java)

    override fun findByUserName(userName: String): User {
        return userRepository.findByUserName(userName).toDomain()
    }
}