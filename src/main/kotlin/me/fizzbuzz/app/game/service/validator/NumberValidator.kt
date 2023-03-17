package me.fizzbuzz.app.game.service.validator

import java.math.BigInteger
import java.math.BigInteger.ZERO

object NumberValidator {
    fun requireNonNegative(numbers: Array<BigInteger>) =
        require(numbers.none { it < ZERO }) { "Numbers should not contains negative" }

    fun requireNoMoreThan(numbers: Array<BigInteger>, limit: Int) =
        require(numbers.size <= limit) { "Collection size (${numbers.size}) is too large (limit: $limit)" }
}