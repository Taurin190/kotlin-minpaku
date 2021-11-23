package com.taurin.minpaku.domain.model.reservation

import com.taurin.minpaku.domain.model.user.User
import com.taurin.minpaku.domain.model.user.UserName
import com.taurin.minpaku.domain.type.Permission
import java.util.*

//TODO APIのレスポンスの出し方に引きずられているため分離する
class Reservation(
    var _title: Title,
    var _checkInDateTime: CheckInDateTime,
    var _checkOutDateTime: CheckOutDateTime?,
    var _url: Url?,
    _user: User? = null,
) {
    private val title: Title = _title
    private val checkInDateTime = _checkInDateTime
    private val checkOutDateTime = _checkOutDateTime ?: checkInDateTime.getDefaultCheckOutDateTime()
    private val url: Url?
    private val user: User

    init {
        verifyCheckInOutDateTime(checkInDateTime, checkOutDateTime)
        url = _url
        //TODO 既存のインタフェース上Guestに設定するが、コンストラクタのインタフェースを見直す
        user = _user ?: User(UserName("Guest"), null, Permission.USER)
    }

    fun toJson(): String {
        val sb = StringBuilder()
        sb.append("{\"user\": \"$user\"")
        sb.append(",\"start\": \"$checkInDateTime\"")
        sb.append(",\"end\": \"$checkOutDateTime\"")

        if (url != null) {
            sb.append(",\"url\": \"$url\"")
        }
        sb.append("}")
        return sb.toString()
    }

    fun getUserProfileName() = user.profile?.name ?: "Guest"

    fun getUrl() = url

    fun getUser() = user

    fun getCheckInDateTime() = checkInDateTime

    fun getCheckOutDateTime() = checkOutDateTime

    // チェックイン・チェックアウトから宿泊日を取得する
    fun getStayDates() : List<Date> {
        val dates = mutableListOf<Date>()
        val checkInDate = checkInDateTime.getDate()
        val checkOutDate = checkOutDateTime.getDate()
        val cal = Calendar.getInstance()
        cal.time = checkInDate
        while(cal.time.before(checkOutDate)) {
            dates.add(cal.time)
            cal.add(Calendar.DATE, 1)
        }

        return dates
    }

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("Reservation [user=$user")
        sb.append(", start=$checkInDateTime")
        sb.append(", end=$checkOutDateTime")

        if (url != null) {
            sb.append(", url=$url")
        }
        sb.append("]")
        return sb.toString()
    }

    private fun verifyCheckInOutDateTime(checkInDateTime: CheckInDateTime, checkOutDateTime: CheckOutDateTime) {
        if (!checkInDateTime.isEarlierThan(checkOutDateTime)) {
            throw IllegalStateException("指定された日付正しい範囲にありません。")
        }

        if (checkInDateTime.getDaysOfStay(checkOutDateTime) > 3) {
            throw IllegalStateException("指定された日付正しい範囲にありません。")
        }
    }
}