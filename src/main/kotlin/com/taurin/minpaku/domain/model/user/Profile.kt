package com.taurin.minpaku.domain.model.user

class Profile(
    val name: Name,
    val emailAddress: EmailAddress,
    val phoneNumber: PhoneNumber,
    val address: Address
) {
    companion object {
        fun create(parameters: Map<String, Any>): Profile {
            return Profile(
                    Name(parameters["name"] as String),
                    EmailAddress(parameters["email_address"] as String),
                    PhoneNumber(parameters["phone_number"] as String),
                    Address(parameters["address"] as String)
            )
        }
    }
}