package qualification

import java.util.*
import java.util.Comparator

/**
 * Created by gabryel on 07/04/17.
 */
fun main(args: Array<String>) {
    val sc = Scanner(System.`in`)

    for (case in 1..sc.nextInt()) {
        println("Case #$case: ${SolverC2().solve(sc.nextLong(), sc.nextLong())}")
    }
}

class SolverC {
    fun solve(userStalls: Long, numberPeople: Long): String {
        val queue = PriorityQueue<Range>()
        queue.add(Range(userStalls))

        var remaining = numberPeople
        while (!queue.isEmpty()) {
            val range = queue.poll()
            if (range.size == 1L) break

            val sub = range.subRanges
            if (--remaining == 0L) {
                return "${sub[1].size} ${sub[0].size}"
            }

            queue.addAll(sub)
        }

        return "0 0"
    }
}

class SolverC2 {
    fun solve(userStalls: Long, numberPeople: Long): String {
        val map = mutableMapOf<Range, Long>()
        val order = PriorityQueue<Range>()
        order.add(Range(userStalls))

        var remaining = numberPeople
        while (!order.isEmpty()) {
            val range = order.poll()
            if (range.size == 1L) break

            val sub = range.subRanges
            val incidents = map[range]?: 1

            remaining -= incidents
            if (remaining <= 0L) {
                return "${sub[1].size} ${sub[0].size}"
            }

            if (!order.contains(sub[0]) && !map.contains(sub[0])) order.add(sub[0])
            if (!order.contains(sub[1]) && !map.contains(sub[1])) order.add(sub[1])

            map.put(sub[0], (map[sub[0]]?: 0) + incidents)
            map.put(sub[1], (map[sub[1]]?: 0) + incidents)
        }

        return "0 0"
    }
}

class Range(val size: Long) : Comparable<Range> {
    val subRanges: List<Range>
        get() {
            val half = (size - 1) / 2
            val subRanges = ArrayList<Range>(2)

            subRanges.add(Range(half))
            subRanges.add(Range(half + if (size % 2 == 0L) 1 else 0))
            return subRanges
        }

    override fun compareTo(other: Range) = other.size.compareTo(size)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as Range

        if (size != other.size) return false

        return true
    }

    override fun hashCode() = size.hashCode()
}