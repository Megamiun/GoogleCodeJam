package br.com.gabryel.qualification

import java.util.*

/**
 * Created by gabryel on 07/04/17.
 */
fun main(args: Array<String>) {
    val sc = Scanner(System.`in`)

    for (case in 1 .. sc.nextInt()) {
        println("Case #$case: ${SolverB().solve(sc.nextLong())}")
    }
}

class SolverB {
    fun solve(lastNumber: Long): Long {
        val digits = lastNumber.toDigitsArray()

        for (index in digits.size - 1 downTo 0) {
            var isTidy = true

            digits.fold(0L, {
                lastDigit, digit ->
                isTidy = isTidy && digit >= lastDigit
                digit
            })

            if (isTidy) break
            if (digits[index] == 9.toLong()) continue

            digits[index] = 9
            digits[index - 1] = (digits[index - 1] - 1) % 10
        }

        return digits.fold(0L, { result, current -> result * 10 + current })
    }

    fun Long.toDigitsArray(): Array<Long> {
        val length = Math.log10(this.toDouble()).toInt() + 1
        val digits = Array(length, { 0L })
        var number: Long = this
        val base = 10

        for (index in length - 1 downTo 0) {
            digits[index] = number % base
            number /= base
        }

        return digits
    }
}