package round1C

/**
 * Created by gabryel on 30/04/17.
 */
import java.util.*

/**
 * Created by gabryel on 07/04/17.
 */
fun main(args: Array<String>) {
    val sc = Scanner(System.`in`).useLocale(Locale.US)

    for (case in 1 .. sc.nextInt()) {
        println("Case #$case: ${SolverC().solve(sc)}")
    }
}

class SolverC {
    fun solve(sc: Scanner): String {
        val cores = sc.nextInt()
        val mustWork = sc.nextInt()
        val units = sc.nextDouble()

        val probabilities = List(cores, {sc.nextDouble()})
        val probability = if (cores == mustWork) forAllCores(probabilities, units) else 0.0

        return String.format(Locale.ENGLISH, "%.12f", probability)
    }

    fun forAllCores(cores: List<Double>, units: Double): Double {
        val queue = LinkedList<Double>(cores.sorted())
        var remainingUnits = units

        var atMinimum = 1
        var minimum = queue.pollFirst()

        while (!queue.isEmpty()) {
            val element = queue.pollFirst()

            if ((element - minimum) * atMinimum <= remainingUnits) {
                remainingUnits -= (element - minimum) * atMinimum
                minimum = element
                atMinimum++
            } else {
                val initial = element * Math.pow(minimum + (remainingUnits / atMinimum), atMinimum.toDouble())
                return queue.fold ( initial, { acc, curr -> curr * acc } )
            }
        }

        return Math.pow(minimum + (remainingUnits / atMinimum), atMinimum.toDouble())
    }
}