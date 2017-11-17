package br.com.gabryel.round1C.a

/**
 * Data about the Stack of pancakes
 */
data class StackStatus(val exposedArea: Double = 0.0, val baseArea: Double = 0.0) {

    /**
     * Add a pancake on the bottom part of the stack... Yes, I know.
     * @return New State of the Stack
     */
    fun add(pancake: Pancake): StackStatus {
        val newExposedArea = exposedArea - baseArea + pancake.totalArea
        return StackStatus(newExposedArea, pancake.upperArea)
    }
}