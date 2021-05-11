package com.taurin.minpaku.Service

import com.taurin.minpaku.Exception.DBException
import com.taurin.minpaku.Repository.ProfileRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ProfileService {
    @Autowired
    private lateinit var profileRepository: ProfileRepository

    private val logger = LoggerFactory.getLogger(AuthService::class.java)

    fun register(username: String, name: String, email: String, address: String, phone: String) {
        try {
            profileRepository.saveWithUserName(
                username,
                name,
                email,
                address,
                phone
            )
        } catch (e: Exception) {
            logger.warn("Profile Register fail with Unexpected Exception: ${e.message}")
            throw DBException("登録出来ませんでした。")
        }
    }
}