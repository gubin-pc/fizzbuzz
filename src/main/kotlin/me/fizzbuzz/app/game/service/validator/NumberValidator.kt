package me.fizzbuzz.app.game.service.validator

import java.math.BigInteger
import java.math.BigInteger.ZERO

object NumberValidator {
    fun requireNotNegative(numbers: Array<BigInteger>) =
        require(numbers.none { it < ZERO }) { "Numbers should not contains negative" }

    fun requireNotLongerThan(numbers: Array<BigInteger>, limit: Int) =
        require(numbers.size <= limit) { "Numbers list with ${numbers.size} size is too long (limit: $limit)" }
}