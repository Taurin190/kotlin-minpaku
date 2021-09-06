package com.taurin.minpaku.unit.datasource

import com.taurin.minpaku.infrastructure.Repository.ReserveRepository
import com.taurin.minpaku.infrastructure.datasource.ReserveDataSource
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.Test

class ReserveDataSourceTest {
    @MockK
    private lateinit var reserveRepository: ReserveRepository

    @InjectMockKs
    private lateinit var reserveDataSource: ReserveDataSource

    @Test
    fun testFindAllByDuration() {

    }
}