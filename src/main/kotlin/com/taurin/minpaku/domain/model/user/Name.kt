package com.taurin.minpaku.domain.model.user

class Name(
    val value: String
) {
    init {
        if (!validate()) {
            throw IllegalStateException("名前は1文字以上、20文字以内で指定して下さい。")
        }
    }

    override fun toString(): String {
        return value
    }

    fun validate(): Boolean {
        if (value.length !in 1..20) {
            return false
        }
        return true
    }
}