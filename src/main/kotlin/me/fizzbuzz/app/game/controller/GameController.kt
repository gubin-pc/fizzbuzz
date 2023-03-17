package me.fizzbuzz.app.game.controller

import me.fizzbuzz.app.game.model.view.FizzbuzzRequest
import me.fizzbuzz.app.game.model.view.FizzbuzzResponse
import me.fizzbuzz.app.game.service.FizzBuzzGame
import me.fizzbuzz.app.game.service.validator.NumberValidator.requireNoMoreThan
import me.fizzbuzz.app.game.service.validator.NumberValidator.requireNonNegative
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class GameController(
    private val fizzBuzzGame: FizzBuzzGame,
    @Value("\${app.game.number.limit}") private val numbersLimit: Int
) {
    @PostMapping(
        "/fizzbuzz",
        consumes = [APPLICATION_JSON_VALUE]
    )
    fun giveNumbers(@RequestBody request: FizzbuzzRequest): FizzbuzzResponse {
        val input = request.numbers.filterNotNull().toTypedArray()
        requireNoMoreThan(input, numbersLimit)
        requireNonNegative(input)

        return FizzbuzzResponse(fizzBuzzGame.play(*input))
    }
}