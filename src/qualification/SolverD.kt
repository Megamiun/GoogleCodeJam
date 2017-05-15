package qualification

import java.util.*

/**
 * Created by gabryel on 07/04/17.
 */
fun main(args: Array<String>) {
    val sc = Scanner(System.`in`)

    for (case in 1 .. sc.nextInt()) {
        println("Case #$case: ${SolverD().solve(sc.nextInt(), sc.nextInt())}")
    }
}

class SolverD {
    fun solve(userStalls: Int, numberPeople: Int): String {
        return ""
    }
}