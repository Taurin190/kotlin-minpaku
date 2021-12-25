package com.taurin.minpaku.infrastructure.datasource

import com.taurin.minpaku.domain.model.user.User
import com.taurin.minpaku.infrastructure.Entity.User as UserEntity
import com.taurin.minpaku.infrastructure.Repository.UserRepository
import org.springframework.stereotype.Repository

@Repository
class UserDataSource(
        val userRepository: UserRepository
) : com.taurin.minpaku.domain.datasource.UserRepository {
    override fun findByUserName(userName: String): User {
        return userRepository.findByUserName(userName).toDomain()
    }

    override fun register(user: User) {
        userRepository.save(UserEntity.fromDomain(user))
    }
}