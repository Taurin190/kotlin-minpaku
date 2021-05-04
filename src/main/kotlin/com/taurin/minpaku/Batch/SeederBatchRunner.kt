package com.taurin.minpaku.Batch

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class SeederBatchRunner: ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
        print("START Seeder batch.")
    }
}