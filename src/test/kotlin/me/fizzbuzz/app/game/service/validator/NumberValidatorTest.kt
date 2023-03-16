package me.fizzbuzz.app.game.service.validator

import me.fizzbuzz.app.game.service.validator.NumberValidator.requireNotLongerThan
import me.fizzbuzz.app.game.service.validator.NumberValidator.requireNotNegative
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
        val error = assertThrows<IllegalArgumentException> { requireNotNegative(numbers) }
        assertEquals("Numbers should not contains negative", error.message)
    }

    @ParameterizedTest
    @ValueSource(strings = [
        "1,2,3,5",
        "1,0,-0",
    ])
    fun `should not throw if numbers are not negative`(
        input: String
    ) {
        //given
        val numbers = input.split(',').map { it.toBigInteger() }.toTypedArray()

        //then
        assertDoesNotThrow { requireNotNegative(numbers) }
    }

    @Test
    fun `should throw if numbers size not longer then expected`() {
        //given
        val numbers = arrayOf(1, 2, 3, 4, 5).map { it.toBigInteger() }.toTypedArray()

        //then
        val error = assertThrows<IllegalArgumentException> { requireNotLongerThan(numbers, 4) }
        assertEquals("Numbers list with 5 size is too long (limit: 4)", error.message)
    }

    @ParameterizedTest
    @ValueSource(strings = [
        "1,2,3,4",
        "1,2,3",
        "1",
    ])
    @NullSource
    fun `should not throw if numbers size not longer then expected`(
        input: String?
    ) {
        //given
        val numbers = input?.split(',')
            ?.map { it.toBigInteger() }
            ?.toTypedArray()
            ?: emptyArray()

        //then
        assertDoesNotThrow { requireNotLongerThan(numbers, 4) }
    }
}