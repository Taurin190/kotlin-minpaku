package com.taurin.minpaku.Repository

import com.taurin.minpaku.Entity.Profile
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface ProfileRepository : JpaRepository<Profile, Long> {
    @Transactional
    @Modifying
    @Query(
        value = "INSERT INTO profile(user_id, name, email, address, phone_number) " +
                "VALUES((SELECT user.user_id FROM user WHERE username = :username), :name, :email, :address, :phone) ;",
        nativeQuery = true
    )
    fun saveWithUserName(
        @Param("username") userName: String,
        @Param("name") name: String,
        @Param("email") email: String,
        @Param("address") address: String,
        @Param("phone") phone: String
    )
}