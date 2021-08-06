package com.taurin.minpaku.unit

import com.github.springtestdbunit.DbUnitTestExecutionListener
import com.taurin.minpaku.Data.Repository.ProfileRepository
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
class ProfileRepositoryTest {
    @Autowired
    private lateinit var profileRepository: ProfileRepository

//    @Test
//    @DatabaseSetup("/dbunit/profile1.xml")
//    fun testFindByUserName() {
//        profileRepository.saveWithUserName(
//            "admin",
//            "アドミン",
//            "admin@example.com",
//            "address",
//            "0123456789"
//        )
//
//        val actual = profileRepository.findByUsername("admin")
//        assertThat(actual?.name).isEqualTo("アドミン")
//        assertThat(actual?.email).isEqualTo("admin@example.com")
//        assertThat(actual?.address).isEqualTo("address")
//    }
}