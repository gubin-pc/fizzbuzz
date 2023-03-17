package me.fizzbuzz.app.game.controller

import me.fizzbuzz.app.FizzbuzzIntegrationTest
import me.fizzbuzz.app.game.controller.SimpleControllerAdvice.ErrorMessage
import me.fizzbuzz.app.game.model.view.FizzbuzzResponse
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.reactive.server.WebTestClient


internal class GameControllerIT : FizzbuzzIntegrationTest() {

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @ParameterizedTest
    @MethodSource("numbers")
    fun `should return expected numbers`(
        number: Array<String>,
        expectedValue: Array<String>
    ) {
        webTestClient.post()
            .uri("/fizzbuzz")
            .contentType(APPLICATION_JSON)
            .bodyValue(createRequest(*number))
            .exchange()
            .expectStatus().isOk
            .expectBody(FizzbuzzResponse::class.java)
            .isEqualTo(FizzbuzzResponse(expectedValue.toList()))
    }

    @Test
    fun `should return bad request if negative number exist`() {
        webTestClient.post()
            .uri("/fizzbuzz")
            .contentType(APPLICATION_JSON)
            .bodyValue(createRequest("1", "2", "-5", "3"))
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
        //given
        val req = createRequest(*input.split(',').toTypedArray())

        //then
        webTestClient.post()
            .uri("/fizzbuzz")
            .contentType(APPLICATION_JSON)
            .bodyValue(req)
            .exchange()
            .expectStatus().isBadRequest
            .expectBody(ErrorMessage::class.java)
            .isEqualTo(ErrorMessage("Input error: 'justString' not a valid number"))
    }

    @Test
    fun `should return bad request if collection size is large`() {
        webTestClient.post()
            .uri("/fizzbuzz")
            .contentType(APPLICATION_JSON)
            .bodyValue(createRequest("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15"))
            .exchange()
            .expectStatus().isBadRequest
            .expectBody(ErrorMessage::class.java)
            .isEqualTo(ErrorMessage("Collection size (15) is too large (limit: 10)"))
    }

    companion object {
        @JvmStatic
        fun numbers() = listOf(
            arguments(
                arrayOf("1", "3", "5", "15"),
                arrayOf("1", "Fizz", "Buzz", "FizzBuzz"),
            ),
            arguments(
                arrayOf(
                    "98464913973759145941857",
                    "295394741921277437825571",
                    "492324569868795729709285",
                    "1476973709606387189127855"
                ),
                arrayOf("98464913973759145941857", "Fizz", "Buzz", "FizzBuzz"),
            ),
            arguments(
                arrayOf(" ", "3", "", "5"),
                arrayOf("Fizz", "Buzz"),
            ),
        )
    }

    private fun createRequest(vararg numbers: String): String {
        val input = numbers.joinToString(separator = ",", prefix = "[", postfix = "]") { "\"$it\"" }
        return """ { "numbers": $input } """.trimIndent()
    }
}