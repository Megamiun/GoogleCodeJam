package br.com.gabryel.qualification

import java.util.*

/**
 * Created by gabryel on 07/04/17.
 */
fun main(args: Array<String>) {
    val sc = Scanner(System.`in`)

    for (case in 1 .. sc.nextInt()) {
        println("Case #$case: ${SolverA().solve(sc.next(), sc.nextInt())}")
    }
}

class SolverA {
    fun solve(pattern: String, flipperSize: Int): String {
        val pancakes = Array(pattern.length, { pattern[it] == '+' })
        var flips = 0

        for (index in 0 .. pancakes.size - flipperSize) {
            if (pancakes[index]) continue

            flips++
            for (marker in index .. index + flipperSize - 1) {
                pancakes[marker] = !pancakes[marker]
            }
        }

        // Verifier().checkPossibility(br.com.gabryel.round1B.State(pancakes), flipperSize)

        return if (pancakes.all { it }) flips.toString() else "IMPOSSIBLE"
    }
}


//class Verifier {
//    fun checkPossibility(state: br.com.gabryel.round1B.State, flipperSize: Int) {
//        val explored = mutableSetOf<br.com.gabryel.round1B.State>()
//        val toBeExplored: Queue<br.com.gabryel.round1B.State> =  LinkedList()
//        toBeExplored.add(state)
//
//        while (!toBeExplored.isEmpty()) {
//            val current = toBeExplored.poll()
//            explored.add(current)
//
//            if (current.valid) {
//                println("It should be possible")
//                return
//            }
//
//            for (item in current.generateStates(flipperSize)) {
//                if (!explored.contains(item) && !toBeExplored.contains(item)) {
//                    toBeExplored.add(item)
//                }
//            }
//        }
//
//        println("It should be impossible")
//    }
//
//    class br.com.gabryel.round1B.State(pancakeState: Array<Boolean>) {
//
//        val state = pancakeState.copyOf()
//
//        val valid = state.all { it }
//
//        fun generateStates(flipperSize: Int): Set<br.com.gabryel.round1B.State>{
//            val newStates = mutableSetOf<br.com.gabryel.round1B.State>()
//            for (index in 0 .. state.size - flipperSize) {
//                val newState = state.copyOf()
//                for (marker in index .. index + flipperSize - 1) {
//                    newState[marker] = !state[marker]
//                }
//
//                newStates.add(br.com.gabryel.round1B.State(newState))
//            }
//
//            return newStates
//        }
//
//        override fun equals(other: Any?): Boolean {
//            if (this === other) return true
//            if (other?.javaClass != javaClass) return false
//
//            other as br.com.gabryel.round1B.State
//
//            if (!Arrays.equals(state, other.state)) return false
//
//            return true
//        }
//
//        override fun hashCode(): Int {
//            return Arrays.hashCode(state)
//        }
//    }
//}