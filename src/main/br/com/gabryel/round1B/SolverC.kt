package br.com.gabryel.round1B

import java.util.*

/**
 * Created by gabryel on 07/04/17.
 */
fun main(args: Array<String>) {
    val sc = Scanner(System.`in`)

    for (case in 1 .. sc.nextInt()) {
        println("Case #$case: ${SolverC().solve(sc)}")
    }
}

class SolverC {
    fun solve(sc: Scanner): String {
        val cities = sc.nextInt()
        val pairs = sc.nextInt()
        val cityArray = Array(cities, {num -> City(num, sc.nextInt(), sc.nextInt()) })

        for (city in 0 .. cities - 1) {
            cityArray[city].destinations = IntArray(cities, {sc.nextInt()})
        }

        var times = ""

        for (pair in 1 .. pairs) {
            val first = cityArray[sc.nextInt() - 1]
            val destiny = cityArray[sc.nextInt() - 1]

            times += String.format(Locale.ENGLISH, "%f ", getTime(destiny, first, cityArray))
        }

        return times.trim()
    }

    private fun getTime(destiny: City, first: City, cityArray: Array<City>) : Double {
        val visited = mutableSetOf<State>()
        val toVisit = PriorityQueue<State>()
        toVisit.add(State(first, 0, 0, 0.0))

        while (!toVisit.isEmpty()) {
            val actual = toVisit.poll()
            val currentCity = actual.city
            visited.add(actual)

            if (currentCity == destiny) {
                return actual.timeSpent
            }

            for (next in 0 .. currentCity.destinations.size - 1) {
                val distance = currentCity.destinations[next]
                if (distance == -1) continue

                val nextStop = cityArray[next]
                val timeSpent = actual.timeSpent

                if (distance <= actual.horseDurability)
                    toVisit.add(createState(actual.horseDurability, actual.horseSpeed, distance, timeSpent, nextStop))

                if (distance <= currentCity.horseDurability)
                    toVisit.add(createState(currentCity.horseDurability, currentCity.horseSpeed, distance, timeSpent, nextStop))
            }
        }

        return 0.0
    }

    private fun createState(durability: Int, speed: Int, distance: Int, timeSpent: Double, next: City) =
            State(next, durability - distance, speed, timeSpent + (distance.toDouble() / speed))
}

class City (val number: Int, val horseDurability: Int, val horseSpeed: Int){
    lateinit var destinations: IntArray

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as City

        if (number != other.number) return false

        return true
    }

    override fun hashCode(): Int {
        return number
    }
}

data class State (val city: City, val horseDurability: Int, val horseSpeed: Int, val timeSpent: Double) : Comparable<State> {

    override fun compareTo(other: State) = timeSpent.compareTo(other.timeSpent)

}