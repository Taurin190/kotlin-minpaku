package com.taurin.minpaku.Service

import com.taurin.minpaku.Repository.ProfileRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ProfileService {
    @Autowired
    private lateinit var profileRepository: ProfileRepository

    fun register(username: String, name: String, email: String, address: String, phone: String) {
        profileRepository.saveWithUserName(
            username,
            name,
            email,
            address,
            phone
        )
    }
}