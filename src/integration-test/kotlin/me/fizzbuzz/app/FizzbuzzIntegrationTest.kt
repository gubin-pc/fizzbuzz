package me.fizzbuzz.app

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@SpringBootTest(webEnvironment = RANDOM_PORT, classes = [FizzbuzzApplication::class])
abstract class FizzbuzzIntegrationTest