package com.taurin.minpaku.Service

import com.taurin.minpaku.Data.Entity.Profile
import com.taurin.minpaku.Exception.DBException
import com.taurin.minpaku.Exception.ProfileNotFound
import com.taurin.minpaku.Data.Repository.ProfileRepository
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