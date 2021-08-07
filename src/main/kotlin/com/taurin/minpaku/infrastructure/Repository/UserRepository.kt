package com.taurin.minpaku.infrastructure.Repository

import com.taurin.minpaku.infrastructure.Entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByUserName(userName: String): User

    @Transactional
    @Modifying
    @Query(value = "INSERT IGNORE INTO user(username, password, permission) VALUES(:username, :password, :permission) ;", nativeQuery = true)
    fun insertIgnore(
        @Param("username") userName: String,
        @Param("password") password: String,
        @Param("permission") permission: Int)
}