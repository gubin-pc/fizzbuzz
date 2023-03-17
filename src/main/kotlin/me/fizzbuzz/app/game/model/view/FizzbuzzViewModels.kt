package me.fizzbuzz.app.game.model.view

import java.math.BigInteger

data class FizzbuzzRequest(val numbers: List<BigInteger?>)

data class FizzbuzzResponse(val values: List<String>)