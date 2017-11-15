package br.com.gabryel.round1A.c.model

import br.com.gabryel.round1A.c.ImpossibleException

sealed class Action {
    object ATTACK : Action() {
        override tailrec fun repeat(turn: Turn, times: Int): Turn {
            if (times == 0) return turn
            if (turn.dragon.canKill(turn.knight)) {
                val knight = turn.knight.beDamaged(turn.dragon.damage)
                return turn.transform(attacks = 1, newKnight = knight, curedLastTurn = false)
            }

            if (turn.knight.canKill(turn.dragon)) {
                if (turn.curedInLastTurn) throw ImpossibleException()

                val dragon = turn.dragon.recoverTo(turn.facts.initDragon.health).beDamaged(turn.knight.damage)
                return repeat(turn.transform(cures = 1, newDragon = dragon, curedLastTurn = true), times)
            }

            val turnsBeforeCure = turn.knight.turnsToKill(turn.dragon) - 1
            val actionsNeeded = Math.min(times, turnsBeforeCure)

            val knight = turn.knight.beDamaged(turn.dragon.damage * turnsBeforeCure)
            val dragon = turn.dragon.beDamaged(turn.knight.damage * turnsBeforeCure)
            val newTurn = turn.transform(attacks = actionsNeeded, newDragon = dragon,
                    newKnight = knight, curedLastTurn = false)
            return repeat(newTurn, times - actionsNeeded)
        }
    }

    object BUFF : Action() {
        override tailrec fun repeat(turn: Turn, times: Int): Turn {
            if (times == 0) return turn

            if (turn.knight.canKill(turn.dragon)) {
                if (turn.curedInLastTurn) throw ImpossibleException()

                val dragon = turn.dragon.recoverTo(turn.facts.initDragon.health).beDamaged(turn.knight.damage)
                return repeat(turn.transform(cures = 1, newDragon = dragon, curedLastTurn = true), times)
            }

            val turnsBeforeCure = turn.knight.turnsToKill(turn.dragon) - 1
            val actionsNeeded = Math.min(times, turnsBeforeCure)

            val dragon = turn.dragon.buff(turn.facts.buff * actionsNeeded)
                    .beDamaged(turn.knight.damage * actionsNeeded)
            val newTurn = turn.transform(buffs = actionsNeeded, newDragon = dragon, curedLastTurn = false)
            return repeat(newTurn, times - actionsNeeded)
        }
    }

    object DEBUFF : Action() {
        override tailrec fun repeat(turn: Turn, times: Int): Turn {
            if (times == 0) return turn

            val debuffedKnight = turn.knight.debuff(turn.facts.debuff)
            if (debuffedKnight.canKill(turn.dragon)) {
                if (turn.curedInLastTurn) throw ImpossibleException()

                val dragon = turn.dragon.recoverTo(turn.facts.initDragon.health).beDamaged(turn.knight.damage)
                return repeat(turn.transform(cures = 1, newDragon = dragon, curedLastTurn = true), times)
            }

            val dragon = turn.dragon.beDamaged(debuffedKnight.damage)
            val newTurn = turn.transform(debuffs = 1, newDragon = dragon, newKnight = debuffedKnight,
                    curedLastTurn = false)
            return repeat(newTurn, times - 1)
        }
    }

    abstract fun repeat(turn: Turn, times: Int): Turn
}