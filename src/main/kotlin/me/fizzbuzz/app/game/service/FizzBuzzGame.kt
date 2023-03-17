package me.fizzbuzz.app.game.service

import java.math.BigInteger

interface FizzBuzzGame {
    fun play(vararg numbers: BigInteger): List<String>
}