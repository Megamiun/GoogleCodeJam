package round1A

import java.util.*

/**
 * Created by gabryel on 14/04/17.
 */
fun main(args: Array<String>) {
    val sc = Scanner(System.`in`)

    for (case in 1 .. sc.nextInt()) {
        println("Case #$case: ${SolverC().solve(RPGChar(sc.nextInt(), sc.nextInt()), RPGChar(sc.nextInt(), sc.nextInt()), sc.nextInt(), sc.nextInt())}")
    }
}

class SolverC {
    fun solve(dragon: RPGChar, knight: RPGChar, buff: Int, debuff: Int): String {
        var turns = 0

        var totalHealth = dragon.health

        while (dragon.isAlive()) {
            val turnDebuff = minOf(debuff, knight.damage)

            var test1= dragon.health / knight.damage
            var test2= knight.health / dragon.damage

            if (knight.damage == 0 || dragon.health / knight.damage >= knight.health / dragon.damage) {
                return (turns + willSlayKnightIn(dragon, knight, buff)).toString()
            }

            if (dragon.health <= knight.damage) {
                if (totalHealth <= knight.damage + (knight.damage - turnDebuff)) {
                    break
                } else {
                    dragon.health = totalHealth
                }
            } else if ((turnDebuff != 0 && knight.damage == turnDebuff) || willDelayEnemy(dragon, knight, turnDebuff)) {
                knight.damage -= turnDebuff
            } else if (willMakeBattleShorter(dragon, knight, buff)) {
                dragon.damage += buff
            } else {
                knight.health -= dragon.damage
            }

            dragon.health -= knight.damage
            turns++
        }

        return "IMPOSSIBLE"
    }

    private fun willSlayKnightIn(dragon: RPGChar, knight: RPGChar, buff: Int): Int {
        var buffed = 0

        while (willMakeBattleShorter(dragon, knight, buff)) {
            dragon.damage += buff
            buffed++
        }

        return Math.ceil(knight.health.toDouble() / dragon.damage).toInt() + buffed
    }

    private fun willMakeBattleShorter(dragon: RPGChar, knight: RPGChar, buff: Int): Boolean {
        return Math.ceil(knight.health.toDouble() / dragon.damage) > 1 + Math.ceil(knight.health.toDouble() / (dragon.damage + buff))
    }

    private fun willDelayEnemy(dragon: RPGChar, knight: RPGChar, debuff: Int): Boolean {
        return Math.ceil(dragon.health.toDouble() / knight.damage) > 1 + Math.ceil(dragon.health.toDouble() / (knight.damage - debuff))
    }
}

data class RPGChar(var health: Int, var damage: Int) {
    fun isAlive() = health > 0
}