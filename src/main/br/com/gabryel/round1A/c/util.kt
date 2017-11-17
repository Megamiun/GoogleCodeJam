package br.com.gabryel.round1A.c

import br.com.gabryel.round1A.c.model.Turn

/**
 * @param attack
 * @param health
 * @return Number of attacks needed to finish with the given health
 */
fun turnsToKill(attack: Int, health: Int) = Math.ceil(health.toDouble() / attack).toInt()

/**
 * Calculates the number of cures needed only for the dragon to buff and attack the needed number of times
 *
 * @param turn Base turn to only buff and attack
 * @return Number of cures, null if impossible to win
 */
fun getNumberOfCures(turn: Turn): Int? {
    val actions = turn.facts.buffsAndAttacks

    val turnsBeforeCureFullHealth = turn.knight.turnsToKill(turn.dragon.initial) - 2
    val firstCureTurn = turn.knight.turnsToKill(turn.dragon)
    val actionsAfterFirstCure = actions - firstCureTurn

    // Can kill before first cure
    if (actions <= firstCureTurn) return 0

    // Can't kill, will have to cure every time
    if (turnsBeforeCureFullHealth < 1) return null

    return 1 + (actionsAfterFirstCure - 1) / turnsBeforeCureFullHealth
}