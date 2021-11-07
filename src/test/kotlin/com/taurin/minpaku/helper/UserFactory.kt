package com.taurin.minpaku.helper

import com.taurin.minpaku.domain.model.user.Profile
import com.taurin.minpaku.domain.model.user.User
import com.taurin.minpaku.domain.model.user.UserName
import com.taurin.minpaku.domain.type.Permission

class UserFactory {
    companion object {
        fun make(): (parameters: Map<String, Any>?) -> User {
            return fun (parameters :Map<String, Any>?): User {
                return User(
                    UserName(parameters?.get("user_name") as String? ?:"testtaro"),
                    if (parameters?.get("profile") != null) parameters["profile"] as Profile else null,
                    Permission.valueOf(parameters?.get("permission") as String? ?: Permission.USER.toString())
                )
            }
        }
    }
}