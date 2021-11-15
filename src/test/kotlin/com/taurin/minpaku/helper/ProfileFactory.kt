package com.taurin.minpaku.helper

import com.taurin.minpaku.domain.model.user.*

class ProfileFactory {
    companion object {
        fun make(): (parameters: Map<String, Any>?) -> Profile {
            return fun(parameters: Map<String, Any>?): Profile {
                return Profile(
                        Name(parameters?.get("name") as String? ?: "Test太郎"),
                        EmailAddress(parameters?.get("email_address") as String? ?: "test@gmail.com"),
                        PhoneNumber(parameters?.get("phone_number") as String? ?: "080-1234-5678"),
                        Address(parameters?.get("address") as String? ?: "東京都千代田区千代田1-1")
                )
            }
        }
    }
}