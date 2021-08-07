package com.taurin.minpaku.batch

import com.taurin.minpaku.infrastructure.Entity.User
import com.taurin.minpaku.enum.Permission
import com.taurin.minpaku.service.AuthService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.core.io.ClassPathResource
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import java.io.BufferedReader
import java.io.InputStreamReader

@ConditionalOnProperty(
    prefix = "application.runner",
    value = ["enabled"],
    havingValue = "true",
    matchIfMissing = true)
@Component
class AdminSeeder(@Autowired val authService: AuthService): ApplicationRunner {
    private val logger = LoggerFactory.getLogger(AdminSeeder::class.java)

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    private var adminList: MutableList<User> = mutableListOf()

    override fun run(args: ApplicationArguments?) {
        logger.info("Batch Start.")
        val inputStream = ClassPathResource("admin.csv").inputStream
        val br = BufferedReader(InputStreamReader(inputStream))
        var line: String?
        logger.info("Read admin users from seed file.")
        while (br.readLine().also { line = it } != null) {
            val adminFactors = line?.split(",")
            if (adminFactors != null) {
                val password = passwordEncoder.encode(adminFactors[1])
                val user = User(
                    userId = null,
                    userName = adminFactors[0],
                    password = password,
                    permission = Permission.valueOf(adminFactors[2])
                )
                adminList.add(user)
            }
        }
        //INSERT IGNOREで重複しないものだけ入力のため順次処理で挿入する
        for (admin in adminList) {
            logger.info("insert ${admin.userName}")
            authService.registerAdminUser(admin)
        }
    }
}