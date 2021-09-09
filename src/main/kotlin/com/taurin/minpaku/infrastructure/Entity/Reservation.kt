package com.taurin.minpaku.infrastructure.Entity

import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

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
}