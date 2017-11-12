package br.com.gabryel.round1A

import java.util.*

/**
 * Created by gabryel on 07/04/17.
 */
fun main(args: Array<String>) {
    val sc = Scanner(System.`in`)

    for (case in 1 .. sc.nextInt()) {
        println("Case #$case: ${SolverA().solve(sc)}")
    }
}

class SolverA {
    fun solve(sc: Scanner): String {
        var rows = sc.nextInt()
        var cols = sc.nextInt()

        var letters = Array(rows, {Array(cols, {'?'})})

        for (row in 0..rows) {
            var line = sc.next().toCharArray()
            for (col in 0..cols) {
                letters[row][col] = line[col]
            }
        }

        return ""
    }
}