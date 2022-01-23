package com.taurin.minpaku.infrastructure.datasource

import com.taurin.minpaku.domain.exception.InvalidParameterException
import com.taurin.minpaku.domain.model.user.Profile
import com.taurin.minpaku.domain.model.user.User
import com.taurin.minpaku.infrastructure.Entity.Profile as ProfileEntity
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
        val userEntity = userRepository.findByUserName(username)
        val currentDomain = userEntity.toDomain()
        if (currentDomain.hasProfile()) {
            throw InvalidParameterException("既にプロフィールが設定されています。")
        }
        val profileEntity = ProfileEntity.fromDomain(profile)
        profileEntity.user = userEntity
        userEntity.profile = profileEntity
        userRepository.save(userEntity)
    }
}