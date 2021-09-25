package com.taurin.minpaku.domain.model.user

class UserName(
    val value: String
) {
    //TODO formと情報が重複しているためconfigにまとめる
    private val regex = Regex(pattern = "(?=.{5,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$")
    init {
        if (!validate()) {
            throw IllegalStateException(
                "UserNameは5文字以上、20文字以内の英数字および._で、先頭は英数字を設定してください。"
            )
        }
    }

    override fun toString(): String {
        return value
    }

    fun validate(): Boolean {
        if (value.length !in 5..20) {
            return false
        }

        if (!regex.matches(value)) {
            return false
        }
        return true
    }
}