package round1B

import java.util.*

/**
 * Created by gabryel on 22/04/17.
 */

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
        val destination = sc.nextDouble()
        val horses = sc.nextInt()

        var minTime = 0.0

        for (horseNum in 1..horses) {
            val horseStart = sc.nextDouble()
            val horseSpeed = sc.nextDouble()

            val timeToFinish = ((destination - horseStart) / horseSpeed)

            minTime = Math.max(minTime, timeToFinish)
        }

        return String.format(Locale.ENGLISH, "%.6f", destination / minTime)
    }
}