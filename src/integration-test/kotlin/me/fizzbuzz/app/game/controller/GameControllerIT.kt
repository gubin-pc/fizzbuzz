package me.fizzbuzz.app.game.controller

import me.fizzbuzz.app.FizzbuzzIntegrationTest
import me.fizzbuzz.app.game.controller.SimpleControllerAdvice.ErrorMessage
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.ParameterizedTypeReference
import org.springframework.test.web.reactive.server.WebTestClient
import java.math.BigInteger


internal class GameControllerIT : FizzbuzzIntegrationTest() {

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @ParameterizedTest
    @MethodSource("numbers")
    fun `should return expected numbers`(
        number: Array<BigInteger>,
        expectedValue: Array<String>
    ) {
        webTestClient.post()
            .uri("/give-numbers")
            .bodyValue(number)
            .exchange()
            .expectStatus().isOk
            .expectBody(object : ParameterizedTypeReference<Array<String>>() {})
            .isEqualTo(expectedValue)
    }

    @Test
    fun `should return bad request if negative number exist`() {
        webTestClient.post()
            .uri("/give-numbers")
            .bodyValue(arrayOf("1", "2", "-5", "3"))
            .exchange()
            .expectStatus().isBadRequest
            .expectBody(ErrorMessage::class.java)
            .isEqualTo(ErrorMessage("Numbers should not contains negative"))
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "1, 2, 3, justString, 4, 5",
            "justString",
        ]
    )
    fun `should return bad request if input is incorrect`(
        input: String
    ) {
        webTestClient.post()
            .uri("/give-numbers")
            .bodyValue(input.split(','))
            .exchange()
            .expectStatus().isBadRequest
            .expectBody(ErrorMessage::class.java)
            .isEqualTo(ErrorMessage("Input error: 'justString' not a valid number"))
    }

    @Test
    fun `should return bad request if numbers size is big`() {
        webTestClient.post()
            .uri("/give-numbers")
            .bodyValue(arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15"))
            .exchange()
            .expectStatus().isBadRequest
            .expectBody(ErrorMessage::class.java)
            .isEqualTo(ErrorMessage("Numbers list with 15 size is too long (limit: 10)"))
    }

    companion object {
        @JvmStatic
        fun numbers() = listOf(
            arguments(
                arrayOf(
                    "1".toBigInteger(),
                    "3".toBigInteger(),
                    "5".toBigInteger(),
                    "15".toBigInteger()
                ),
                arrayOf("1", "Fizz", "Buzz", "FizzBuzz"),
            ),
            arguments(
                arrayOf(
                    "98464913973759145941857".toBigInteger(),
                    "295394741921277437825571".toBigInteger(),
                    "492324569868795729709285".toBigInteger(),
                    "1476973709606387189127855".toBigInteger()
                ),
                arrayOf("98464913973759145941857", "Fizz", "Buzz", "FizzBuzz"),
            ),
            arguments(
                arrayOf(
                    null,
                    "3".toBigInteger(),
                    null,
                    "5".toBigInteger(),
                    null
                ),
                arrayOf("Fizz", "Buzz"),
            ),
        )
    }
}