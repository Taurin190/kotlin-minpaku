package com.taurin.minpaku.domain.model.user

import com.taurin.minpaku.domain.type.Permission

class User(
    val userName: UserName,
    var profile: Profile?,
    val permission: Permission
) {
}