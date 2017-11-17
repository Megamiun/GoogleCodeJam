package br.com.gabryel.round1C.a

import br.com.gabryel.util.double.toGoogleFormat
import java.util.*

/**
 * Created by gabryel on 07/04/17.
 */
fun main(args: Array<String>) {
    val sc = Scanner(System.`in`)

    for (case in 1 .. sc.nextInt()) {
        println("Case #$case: ${solve(sc).toGoogleFormat()}")
    }
}

/**
 * Scan the number of pancakes passed, followed by the number of pancakes to select,
 * followed by a series of radius and heights
 *
 * @return Exposed area of the pancakes
 */
fun solve(sc: Scanner): Double {
    val passedPancakes = sc.nextInt()
    val numberOfPancakesToSelect = sc.nextInt()

    return createPancakes(passedPancakes, sc)
            .getBiggest(numberOfPancakesToSelect)
            .stackPancakes()
            .exposedArea
}

/**
 * @return List of all declared pancakes
 */
private fun createPancakes(pancakes: Int, sc: Scanner) =
        List(pancakes, { Pancake(sc.nextInt(), sc.nextInt()) })

/**
 * @return Status of the pancake stack
 */
private fun List<Pancake>.stackPancakes(): StackStatus {
    return this
            .sortedBy { it.radius }
            .fold(StackStatus(), { prev, pancake -> prev.add(pancake) })
}

/**
 * @param pancakes number of pancakes to select
 * @return First N pancakes ordered by totalArea
 */
private fun List<Pancake>.getBiggest(pancakes: Int) =
        this.sortedByDescending { it.totalArea }.slice(0 until pancakes)