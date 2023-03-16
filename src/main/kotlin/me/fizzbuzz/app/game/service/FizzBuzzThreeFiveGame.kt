package me.fizzbuzz.app.game.service

import org.springframework.stereotype.Service
import java.math.BigInteger
import java.math.BigInteger.ZERO

@Service
class FizzBuzzThreeFiveGame : FizzBuzzGame {

    override fun play(vararg numbers: BigInteger) = numbers.map(::getFizzBuzzValue).toTypedArray()

    private fun getFizzBuzzValue(number: BigInteger) = when {
        ZERO == number.mod(THREE * FIVE) -> FIZZ + BUZZ
        ZERO == number.mod(THREE) -> FIZZ
        ZERO == number.mod(FIVE) -> BUZZ
        else -> number.toString()
    }

    companion object {
        private const val FIZZ = "Fizz"
        private const val BUZZ = "Buzz"
        private val THREE = 3.toBigInteger()
        private val FIVE = 5.toBigInteger()
    }
}