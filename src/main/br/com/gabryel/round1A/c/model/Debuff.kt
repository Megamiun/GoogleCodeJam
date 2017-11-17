package br.com.gabryel.round1A.c.model

object Debuff {
    tailrec fun debuff(turn: Turn, times: Int, curedInLastTurn: Boolean = false): Turn? {
        val debuffedKnight = turn.knight.debuff(turn.facts.debuff)

        if (times == 0) return turn
        if (debuffedKnight.canKill(turn.dragon)) {
            if (curedInLastTurn) return null

            val dragon = turn.dragon.cure().takeDamage(turn.knight.damage)
            return debuff(turn.add(cures = 1, newDragon = dragon), times, true)
        }

        val turnsBeforeCureFullHealth = debuffedKnight.turnsToKill(turn.facts.initDragon) - 2
        val turnsBeforeCure = debuffedKnight.turnsToKill(turn.dragon) - 1
        val cycles = (times / turnsBeforeCure) - 1

        if (cycles > 1 && turnsBeforeCure == turnsBeforeCureFullHealth) {
            val debuffs = cycles * turnsBeforeCureFullHealth
            val knight = turn.knight.debuff(debuffs * turn.facts.debuff)
            val dragon = turn.dragon.cure().takeDamage(knight.damage)

            val newTurn = turn.add(cures = cycles, debuffs = debuffs, newKnight = knight,
                    newDragon = dragon)
            return debuff(newTurn, times - debuffs)
        }

        val dragon = turn.dragon.takeDamage(debuffedKnight.damage)
        val newTurn = turn.add(debuffs = 1, newDragon = dragon, newKnight = debuffedKnight)
        return debuff(newTurn, times - 1)
    }
}