package com.taurin.minpaku.infrastructure.datasource

import com.taurin.minpaku.domain.exception.InvalidParameterException
import com.taurin.minpaku.domain.model.user.Profile
import com.taurin.minpaku.domain.model.user.User
import com.taurin.minpaku.infrastructure.Entity.User as UserEntity
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

    override fun registerProfile(username: String, profile: Profile) {
        val currentUserEntity = userRepository.findByUserName(username)
        val currentDomain = currentUserEntity.toDomain()
        if (currentDomain.hasProfile()) {
            throw InvalidParameterException("既にプロフィールが設定されています。")
        }
        val userWithProfile = User.create(
                mapOf(
                        "user_name" to currentDomain.userName.toString(),
                        "profile" to profile,
                        "permission" to currentDomain.permission
                )
        )
        val updatedUserEntity = UserEntity.fromDomain(userWithProfile, currentUserEntity)
        userRepository.save(updatedUserEntity)
    }
}