package com.taurin.minpaku.unit.datasource

import com.taurin.minpaku.infrastructure.Entity.Book
import com.taurin.minpaku.infrastructure.Entity.Reservation
import com.taurin.minpaku.infrastructure.Repository.ReserveRepository
import com.taurin.minpaku.infrastructure.datasource.ReserveDataSource
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.sql.Date
import java.time.LocalDate
import java.time.LocalDateTime

class ReserveDataSourceTest {
    @MockK
    private lateinit var reserveRepository: ReserveRepository

    @InjectMockKs
    private lateinit var reserveDataSource: ReserveDataSource

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun testFindAllByDuration() {
        val list = mutableListOf<Reservation>()
        val reservation1 = Reservation(
            1,
            null,
            mutableListOf<Book>(),
            LocalDateTime.parse("2021-01-01T15:00:00"),
            LocalDateTime.parse("2021-01-02T10:00:00")
        )
        val reservation2 = Reservation(
            2,
            null,
            mutableListOf<Book>(),
            LocalDateTime.parse("2021-01-02T15:00:00"),
            LocalDateTime.parse("2021-01-04T10:00:00")
        )
        val book1 = Book(1, null, 1, Date.valueOf("2021-01-01"))
        val book2 = Book(2, null, 1, Date.valueOf("2021-01-02"))
        val book3 = Book(2, null, 1, Date.valueOf("2021-01-03"))
        reservation1.books = mutableListOf(book1)
        reservation2.books = mutableListOf(book2, book3)
        list.add(reservation1)
        list.add(reservation2)

        every {
            reserveRepository.findAllByDuration(any(), any())
        } returns list

        val actual = reserveDataSource.findAllByDuration(
            LocalDate.parse("2021-01-01"),
            LocalDate.parse("2021-01-31")
        )

        // Collectionオブジェクトの検証なので詳細の検証は行わない
        assertThat(actual).toString().isNotEmpty()
        assertThat(actual.count()).isEqualTo(2)
    }
}