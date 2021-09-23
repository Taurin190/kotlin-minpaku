package com.taurin.minpaku.domain.model.user

class PhoneNumber(
    val value: String
) {
    //TODO formと情報が重複しているためconfigにまとめる
    private val regex = Regex(pattern = "^0[0-9/-]{11}")
    init {
        if (!validate()) {
            throw IllegalStateException(
                "電話番号は12文字以上、13文字以内の数字および-で、先頭は0を設定してください。"
            )
        }
    }

    override fun toString(): String {
        return value
    }

    fun validate(): Boolean {
        if (value.length !in 12..13) {
            return false
        }

        if (!regex.containsMatchIn(value)) {
            return false
        }
        return true
    }
}