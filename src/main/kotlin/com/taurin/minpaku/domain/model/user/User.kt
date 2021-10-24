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
        if (profile != null) {
            return profile.name.toString()
        }
        return "Guest"
    }
}