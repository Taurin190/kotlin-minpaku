package com.taurin.minpaku.Entity

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "charge")
class Charge (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @OneToOne(cascade = [CascadeType.REMOVE])
    @JoinColumn(name = "book_id")
    var book: Book? = null,
    @Column(columnDefinition = "int default 2000")
    var fee: Int = 0
) : Base()