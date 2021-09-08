package com.taurin.minpaku.infrastructure.Entity

import java.util.Date
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
    var stayDate: Date = Date()
) : Base()