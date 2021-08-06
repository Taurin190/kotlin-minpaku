package com.taurin.minpaku.integration

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.junit.jupiter.api.Test
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*


@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = ["application.runner.enabled=true"]
)
@AutoConfigureMockMvc
class ProfileControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun testAccessProfileWithoutAuth() {
        mockMvc.perform(get("/profile"))
            .andExpect(status().is3xxRedirection)
            .andExpect(redirectedUrlPattern("http://*/login"))
    }
}