package com.taurin.minpaku.unit

import com.github.springtestdbunit.DbUnitTestExecutionListener
import com.taurin.minpaku.infrastructure.Repository.UserRepository
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener

@ExtendWith(SpringExtension::class)
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = ["application.runner.enabled=false"]
)
@TestExecutionListeners(
    listeners = [
        DependencyInjectionTestExecutionListener::class,
        DbUnitTestExecutionListener::class
    ]
)
class UserRepositoryTest {
    @Autowired
    private lateinit var userRepository: UserRepository

//    @Test
//    @DatabaseSetup("/dbunit/auth1.xml")
//    fun testFindByUserName() {
//        var actual = userRepository.findByUserName("admin")
//        assertThat(actual.userId).isEqualTo(1)
//        assertThat(actual.permission).isEqualTo(Permission.ADMIN)
//
//        actual = userRepository.findByUserName("user1")
//        assertThat(actual.userId).isEqualTo(2)
//        assertThat(actual.permission).isEqualTo(Permission.USER)
//    }
//
//    @Test
//    @DatabaseSetup("/dbunit/auth1.xml")
//    fun testFindByUserNameNotFound() {
//        try {
//            userRepository.findByUserName("invalid")
//            fail<java.lang.AssertionError>("Didn't throw exception without data")
//        } catch (e: Exception) {
//            assertThat(e.message).isEqualTo("Result must not be null!")
//        }
//    }
//
//    @Test
//    @DatabaseSetup("/dbunit/auth1.xml")
//    fun testInsertAndFind() {
//        userRepository.insertIgnore("newuser", "newuser", 0)
//
//        val actual = userRepository.findByUserName("newuser")
//        assertThat(actual.permission).isEqualTo(Permission.USER)
//    }
}