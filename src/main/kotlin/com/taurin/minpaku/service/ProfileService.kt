package com.taurin.minpaku.service

import com.taurin.minpaku.data.Entity.Profile
import com.taurin.minpaku.exception.DBException
import com.taurin.minpaku.exception.ProfileNotFound
import com.taurin.minpaku.data.Repository.ProfileRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ProfileService {
    @Autowired
    private lateinit var profileRepository: ProfileRepository

    private val logger = LoggerFactory.getLogger(ProfileService::class.java)

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

    fun findByUsername(username: String) : Profile {
        try {
            val profile = profileRepository.findByUsername(username)
            if (profile != null) {
                return profile
            }
        } catch (e: Exception) {
            logger.error("findByUsername fail with Unexpected Exception: ${e.message}")
            throw DBException("ユーザ情報を取得できませんでした。")
        }
        throw ProfileNotFound("ユーザ情報が設定されていません。")
    }
}