package com.taurin.minpaku.integration

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = ["application.runner.enabled=true"]
)
@AutoConfigureMockMvc
class ReservationListControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun testShowReservationList() {
        mockMvc.perform(MockMvcRequestBuilders.get("/reservation/list"))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful)
    }
}