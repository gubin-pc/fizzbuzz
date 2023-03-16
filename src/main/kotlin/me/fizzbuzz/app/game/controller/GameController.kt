package me.fizzbuzz.app.game.controller

import me.fizzbuzz.app.game.service.FizzBuzzGame
import me.fizzbuzz.app.game.service.validator.NumberValidator.requireNotLongerThan
import me.fizzbuzz.app.game.service.validator.NumberValidator.requireNotNegative
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.math.BigInteger

@RestController
class GameController(
    private val fizzBuzzGame: FizzBuzzGame,
    @Value("\${app.game.number.limit}") private val numbersLimit: Int
) {
    @PostMapping(
        "/give-numbers",
        consumes = [APPLICATION_JSON_VALUE]
    )
    fun giveNumbers(@RequestBody numbers: Array<BigInteger?>): Array<String> {
        val input = numbers.filterNotNull().toTypedArray()
        requireNotLongerThan(input, numbersLimit)
        requireNotNegative(input)
        return fizzBuzzGame.play(*input)
    }
}