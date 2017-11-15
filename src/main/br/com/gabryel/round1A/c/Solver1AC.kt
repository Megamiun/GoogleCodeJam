package br.com.gabryel.round1A.c

import br.com.gabryel.round1A.c.model.*
import java.util.*

fun main(args: Array<String>) {
    val sc = Scanner(System.`in`)

    for (case in 1..sc.nextInt()) {
        println("Case #$case: ${solve(sc)}")
    }
}

fun solve(sc: Scanner): Result {
    val facts = BattleFacts(RPGChar(sc.nextInt(), sc.nextInt()), RPGChar(sc.nextInt(), sc.nextInt()),
            sc.nextInt(), sc.nextInt())

    return try {
        Turn(facts)
                .debuffPhase()
                .mapNotNull { it.buffAndAttack() }
                .minBy(Turn::turns)
                ?.turns.getResult()
    } catch (e: ImpossibleException) {
        Result.Impossible
    }
}

/**
 * Buff the needed number of times and then attack the needed number of times,
 * using the BattleFacts calculations as basis.
 * @return Turn that the knight dies. Null in impossible cases.
 */
private fun Turn.buffAndAttack(): Turn? {
    return try {
        act(Action.BUFF, facts.buffsNeeded).act(Action.ATTACK, facts.attacksNeeded)
    } catch (e: ImpossibleException) {
        null
    }
}

/**
 * Tries to find all debuffs threshold, that are values of the knight attack power that
 * add at least one more attack for the knight to completely deplete the dragons life.
 * Uses tail call recursion to avoid StackOverflows, using CPS for that:
 * https://stackoverflow.com/questions/47255833/tail-rec-kotlin-list?noredirect=1#comment81463221_47255833
 * @return All possible debuffed States
 */
private tailrec fun Turn.debuffPhase(acc: List<Turn> = emptyList()): List<Turn> {
    val turns = acc + this
    if (facts.debuff == 0 || knight.damage == 0) {
        return turns
    }

    // Recursively find all possible thresholds of debuffing
    return act(Action.DEBUFF, debuffsForNextThreshold()).debuffPhase(turns)
}

/**
 * @return Minimum number of debuffs needed to transport to next threshold of damage.
 * A threshold of damage is a moment that the enemy damage changes the number of hits needed
 * to kill the dragon.
 */
private fun Turn.debuffsForNextThreshold(): Int {
    val turnsToDie = knight.turnsToKill(facts.initDragon)
    val nextThreshold = Math.ceil(facts.initDragon.health.toDouble() / turnsToDie) - 1
    return Math.ceil((knight.damage - nextThreshold) / facts.debuff).toInt()
}

private fun Int?.getResult() = if (this != null) Result.Sucess(this) else Result.Impossible