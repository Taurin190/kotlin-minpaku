package com.taurin.minpaku.domain.model.user

import com.taurin.minpaku.domain.type.Permission

class User(
    val userName: UserName,
    val profile: Profile?,
    val permission: Permission
) {
    fun hasProfile() = profile != null

    fun isAdmin() = permission == Permission.ADMIN

    override fun toString(): String {
        return (profile?.name ?: "Guest").toString()
    }

    companion object {
        fun create(parameters: Map<String, Any>): User {
            return User(
                    UserName(parameters["user_name"] as String),
                    parameters["profile"] as Profile,
                    parameters["permission"] as Permission
            )
        }
    }
}