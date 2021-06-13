package com.taurin.minpaku.Integration

import com.github.springtestdbunit.DbUnitTestExecutionListener
import com.github.springtestdbunit.annotation.DatabaseSetup
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@RunWith(SpringRunner::class)
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = ["application.runner.enabled=false"]
)
@AutoConfigureMockMvc
@TestExecutionListeners(
    listeners = [
        DependencyInjectionTestExecutionListener::class,
        DbUnitTestExecutionListener::class
    ]
)
class AuthControllerWithDBMockTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    @DatabaseSetup("/dbunit/auth1.xml")
    fun testRegisterWithValidParams() {
        mockMvc.perform(
            post("/register")
                .param("username", "testuser1")
                .param("password", "testuser1")
                .param("passwordMatch", "testuser1"))
            .andExpect(MockMvcResultMatchers.status().is2xxSuccessful)
    }
}