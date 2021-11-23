package com.taurin.minpaku.infrastructure.Entity

import com.taurin.minpaku.domain.model.reservation.CheckInDateTime
import com.taurin.minpaku.domain.model.reservation.CheckOutDateTime
import com.taurin.minpaku.domain.model.reservation.Title
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*
import com.taurin.minpaku.domain.model.reservation.Reservation as ReservationDomain

@Entity
@Table(name = "reservation")
class Reservation(
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

    fun toDomain(): ReservationDomain {
        return ReservationDomain(
                Title(user?.userName ?: "Guest"),
                CheckInDateTime(checkInDateTime),
                CheckOutDateTime(checkOutDateTime),
                null
        )
    }

    companion object {
        fun fromDomain(reservationDomain: ReservationDomain): Reservation {
            return Reservation(
                    null,
                    User.fromDomain(reservationDomain.getUser()),
                    getBookListFromReservation(reservationDomain),
                    reservationDomain.getCheckInDateTime().value,
                    reservationDomain.getCheckOutDateTime().value
            )
        }

        private fun getBookListFromReservation(reservationDomain: ReservationDomain): List<Book> {
            val bookList = mutableListOf<Book>()
            reservationDomain.getStayDates().forEach {
                bookList.add(Book(
                        null,
                        null,
                        //TODO 固定値でなくReservationから宿泊人数取れるように変更
                        1,
                        it
                ))
            }
            return bookList
        }
    }
}