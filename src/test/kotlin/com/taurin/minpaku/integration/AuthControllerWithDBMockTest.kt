package com.taurin.minpaku.integration

import com.github.springtestdbunit.DbUnitTestExecutionListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener
import org.springframework.test.web.servlet.MockMvc

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

// TODO IntegrationテストでDBモックを使えるように修正する。

//    @Test
//    @DatabaseSetup("/dbunit/auth1.xml")
//    fun testRegisterWithValidParams() {
//        mockMvc.perform(
//            post("/register")
//                .param("username", "testuser1")
//                .param("password", "testuser1")
//                .param("passwordMatch", "testuser1"))
//            .andExpect(MockMvcResultMatchers.status().is2xxSuccessful)
//    }
}