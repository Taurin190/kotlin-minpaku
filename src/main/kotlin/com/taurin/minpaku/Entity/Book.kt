package com.taurin.minpaku.Entity

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "book")
class Book (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var bookId: Long? = null,
    @ManyToOne
    @JoinColumn(name = "reservation_id", nullable = false)
    var reservation: Reservation? = null,
    @Column(columnDefinition = "int default 1")
    var guestNum: Int = 1,
    @Column(nullable = false, unique = true)
    var stayDate: Date? = null
) : Base()