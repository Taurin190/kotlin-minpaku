package com.taurin.minpaku.Service

import com.taurin.minpaku.Entity.Profile
import com.taurin.minpaku.Entity.User
import com.taurin.minpaku.Repository.ProfileRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ProfileService {
    @Autowired
    private lateinit var profileRepository: ProfileRepository

    fun register(user: User, name: String, email: String, address: String, phone: String) {
        val profile = Profile(
            null,
            user,
            name,
            email,
            address,
            phone
        )
        profileRepository.save(profile)
    }
}