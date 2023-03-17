package me.fizzbuzz.app.game.service.validator

import me.fizzbuzz.app.game.service.validator.NumberValidator.requireNoMoreThan
import me.fizzbuzz.app.game.service.validator.NumberValidator.requireNonNegative
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.NullSource
import org.junit.jupiter.params.provider.ValueSource

internal class NumberValidatorTest {

    @ParameterizedTest
    @ValueSource(strings = [
        "1,2,-3,5",
        "-1"
    ])
    fun `should throw if negative numbers exist`(
        input: String
    ) {
        //given
        val numbers = input.split(',').map { it.toBigInteger() }.toTypedArray()

        //then
        val error = assertThrows<IllegalArgumentException> { requireNonNegative(numbers) }
        assertEquals("Numbers should not contains negative", error.message)
    }

    @ParameterizedTest
    @ValueSource(strings = [
        "1,2,3,5",
        "1,0,-0",
    ])
    fun `should not throw if numbers positive or zero`(
        input: String
    ) {
        //given
        val numbers = input.split(',').map { it.toBigInteger() }.toTypedArray()

        //then
        assertDoesNotThrow { requireNonNegative(numbers) }
    }

    @Test
    fun `should throw if collection size larger then required`() {
        //given
        val numbers = arrayOf(1, 2, 3, 4, 5).map { it.toBigInteger() }.toTypedArray()

        //then
        val error = assertThrows<IllegalArgumentException> { requireNoMoreThan(numbers, 4) }
        assertEquals("Collection size (5) is too large (limit: 4)", error.message)
    }

    @ParameterizedTest
    @ValueSource(strings = [
        "1,2,3,4",
        "1,2,3",
        "1",
    ])
    @NullSource
    fun `should not throw if collection size less or equals then required`(
        input: String?
    ) {
        //given
        val numbers = input?.split(',')
            ?.map { it.toBigInteger() }
            ?.toTypedArray()
            ?: emptyArray()

        //then
        assertDoesNotThrow { requireNoMoreThan(numbers, 4) }
    }
}