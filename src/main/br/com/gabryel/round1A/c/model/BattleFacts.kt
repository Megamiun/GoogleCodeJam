package br.com.gabryel.round1A.c.model

import br.com.gabryel.round1A.c.turnsToKill

class BattleFacts(val initDragon: RPGChar, val initKnight: RPGChar, val buff: Int, val debuff: Int) {

    val buffsNeeded = discoverBuffsNeeded(initDragon)

    val attacksNeeded = turnsToKill(initDragon.damage + (buffsNeeded * buff), initKnight.health)

    /**
     * @return Number of buffs needed to optimize the process of killing the knight,
     * that is when the buff doesn't make you save at least one attack against the knight
     */
    private fun discoverBuffsNeeded(dragon: RPGChar): Int {
        val buffedDragon = dragon.buff(buff)
        if (dragon.turnsToKill(initKnight) < 1 + buffedDragon.turnsToKill(initKnight)) {
            return 0
        }

        return discoverBuffsNeeded(buffedDragon) + 1
    }
}