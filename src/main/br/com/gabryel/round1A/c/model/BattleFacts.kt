package br.com.gabryel.round1A.c.model

import br.com.gabryel.round1A.c.turnsToKill

class BattleFacts(val initDragon: RPGChar, val initKnight: RPGChar, val buff: Int, val debuff: Int) {

    val buffsNeeded = initDragon.discoverBuffsNeeded()

    val attacksNeeded = initDragon.buff(buffsNeeded * buff).turnsToKill(initKnight)

    /**
     * @return Number of buffs needed to optimize the process of killing the knight,
     * that is when the buff doesn't make you save at least one attack against the knight
     */
    private tailrec fun RPGChar.discoverBuffsNeeded(buffs: Int = 0): Int {
        val buffedDragon = buff(buff)
        if (turnsToKill(initKnight) == buffedDragon.turnsToKill(initKnight)) {
            return buffs
        }

        return buffedDragon.discoverBuffsNeeded(1 + buffs)
    }
}