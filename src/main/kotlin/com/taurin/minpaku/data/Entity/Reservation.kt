package com.taurin.minpaku.data.Entity

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
    var books: List<Book> = mutableListOf<Book>()
) : Base() {
    fun addBookFromCheckInOut(bookDates: List<Date>, guestNum: Int = 1) {
        val bookList = mutableListOf<Book>()
        bookDates.forEach {
            bookList.add(Book(null, this, guestNum, it))
        }
        this.books = bookList
    }
}