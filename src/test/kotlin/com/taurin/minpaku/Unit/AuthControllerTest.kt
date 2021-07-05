package com.taurin.minpaku.Unit

import com.taurin.minpaku.Presentation.Controller.AuthController
import com.taurin.minpaku.Service.AuthService
import io.mockk.MockKAnnotations
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@ExtendWith(SpringExtension::class)
@WebMvcTest(AuthController::class)
class AuthControllerTest {
    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var authService: AuthService

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun testLoginForm() {
        mockMvc.perform(
            get("/login"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().is2xxSuccessful)
    }
}