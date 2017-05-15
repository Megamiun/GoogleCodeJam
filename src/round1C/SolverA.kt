package round1C

import java.util.*

/**
 * Created by gabryel on 30/04/17.
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
        val pancakes = sc.nextInt()
        val size = sc.nextInt()

        val pancakeList = mutableListOf<Pancake>()

        for (pancake in 1 .. pancakes) {
            pancakeList.add(Pancake(sc.nextInt(), sc.nextInt()))
        }

        val selectedGroupedByWidth = pancakeList
                .sortedBy { it.totalArea }
                .filterIndexed { index, _ -> index >= pancakeList.size - size }
                .sortedBy { it.radius }

        var total = 0.0
        var coveredByLast = 0.0
        for (pancake in selectedGroupedByWidth) {
            total += pancake.totalArea - coveredByLast
            coveredByLast = pancake.upperArea
        }

        // TODO Inelegant, but it will do today
        return String.format(Locale.ENGLISH, "%.25f", total)
    }
}

data class Pancake(val radius: Int, val height: Int) : Comparable<Pancake> {

    val upperArea = Math.PI * Math.pow(radius.toDouble(), 2.0)

    val sideArea = 2 * Math.PI * radius * height

    val totalArea = upperArea + sideArea

    override fun compareTo(other: Pancake): Int {
        return totalArea.compareTo(this.totalArea)
    }
}