package com.taurin.minpaku.infrastructure.Entity

import com.taurin.minpaku.domain.reservation.CheckInDateTime
import com.taurin.minpaku.domain.reservation.CheckOutDateTime
import com.taurin.minpaku.domain.reservation.Title
import com.taurin.minpaku.domain.reservation.Url
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*
import com.taurin.minpaku.domain.reservation.Reservation as ReservationDomain

@Entity
@Table(name = "reservation")
class Reservation (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var reservationId: Long? = null,
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    var user: User? = null,
    @OneToMany(mappedBy = "reservation", cascade = [CascadeType.ALL], orphanRemoval = true)
    var books: List<Book> = mutableListOf<Book>(),
    @Column(nullable = false)
    var checkInDateTime: LocalDateTime = LocalDateTime.parse("2000-01-01T15:00:00"),
    @Column(nullable = false)
    var checkOutDateTime: LocalDateTime = LocalDateTime.parse("2000-01-02T10:00:00")
) : Base() {
    fun addBookFromCheckInOut(bookDates: List<Date>, guestNum: Int = 1) {
        val bookList = mutableListOf<Book>()
        bookDates.forEach {
            bookList.add(Book(null, this, guestNum, it))
        }
        this.books = bookList
    }

    fun toDomain() : ReservationDomain {
        return ReservationDomain(
            Title(user?.userName ?: "Guest"),
            CheckInDateTime(checkInDateTime),
            CheckOutDateTime(checkOutDateTime),
            null
        )
    }

    companion object {
        fun fromDomain(reservationDomain: ReservationDomain) : Reservation {
            return Reservation(
                null,
                null,
                mutableListOf<Book>(),
                reservationDomain.checkInDateTime.value,
                reservationDomain.checkOutDateTime.value
            )
        }
    }
}