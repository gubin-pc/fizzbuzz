package me.fizzbuzz.app.game.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.MethodSource
import java.math.BigInteger

internal class FizzBuzzThreeFiveGameTest {

    private val fizzBuzzGame: FizzBuzzGame = FizzBuzzThreeFiveGame()

    @ParameterizedTest
    @CsvSource(
        "1, 1",
        "3, Fizz",
        "5, Buzz",
        "15, FizzBuzz",
        "98464913973759145941857, 98464913973759145941857",
        "295394741921277437825571, Fizz",
        "492324569868795729709285, Buzz",
        "1476973709606387189127855, FizzBuzz",
    )
    fun `should return expected result for one number`(
        number: BigInteger,
        expectedValue: String
    ) {
        assertThat(fizzBuzzGame.play(number))
            .singleElement()
            .isEqualTo(expectedValue)
    }

    @ParameterizedTest
    @MethodSource("numbers")
    fun `should return expected result for several numbers`(
        number: Array<BigInteger>,
        expectedValue: Array<String>
    ) {
        assertThat(fizzBuzzGame.play(*number))
            .hasSameSizeAs(expectedValue)
            .usingRecursiveComparison()
            .isEqualTo(expectedValue)
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
        )
    }
}