package br.com.gabryel.round1A.c.model

import br.com.gabryel.round1A.c.ImpossibleException
import br.com.gabryel.round1A.c.turnsToKill

class NextTurn(turn: Turn, action: Action, times: Int) : Turn(turn.facts) {
    override val cures: Int
    override val buffs: Int = turn.buffs + if (action == Action.BUFF) times else 0
    override val debuffs: Int = turn.debuffs + if (action == Action.DEBUFF) times else 0
    override val attacks: Int = turn.attacks + if (action == Action.ATTACK) times else 0

    override val dragon: RPGChar
    override val knight: RPGChar

    init {
        val totalHealth = facts.initDragon.health
        var lastDragon = turn.dragon
        var lastKnight = turn.knight

        var curesNeeded = 0
        var timesRemaining = times
        var dragonHP = lastDragon.health
        var knightDamage = lastKnight.damage
        when (action) {
            Action.DEBUFF -> {
                while (timesRemaining > 0) {
                    val simulatedKnightDamage = Math.max(0, knightDamage - facts.debuff)
                    val turnsBeforeNextCure = turnsToKill(simulatedKnightDamage, dragonHP) - 1
                    val commonTurnsBeforeNextCure = turnsToKill(simulatedKnightDamage, totalHealth) - 2

                    if (turnsBeforeNextCure < 1 && commonTurnsBeforeNextCure < 1) {
                        throw ImpossibleException()
                    }

                    if (turnsBeforeNextCure == 0) {
                        dragonHP = totalHealth - knightDamage
                        curesNeeded++
                        continue
                    }

                    val cycles = (timesRemaining / turnsBeforeNextCure) - 1
                    if (cycles > 1 && turnsBeforeNextCure == commonTurnsBeforeNextCure) {
                        knightDamage = Math.max(0, knightDamage - (cycles * turnsBeforeNextCure * facts.debuff))
                        timesRemaining -= cycles * turnsBeforeNextCure
                        dragonHP = totalHealth - knightDamage
                        curesNeeded += cycles
                    } else {
                        knightDamage = simulatedKnightDamage
                        dragonHP -= knightDamage
                        timesRemaining--
                    }
                }

                lastKnight = lastKnight.debuff(times * facts.debuff)
            }
            Action.BUFF -> {
                var curedInLastTurn = false
                while (timesRemaining > 0) {
                    curedInLastTurn = if (dragonHP <= knightDamage) {
                        if (curedInLastTurn) throw ImpossibleException()

                        dragonHP = totalHealth
                        curesNeeded++
                        true
                    } else {
                        timesRemaining--
                        false
                    }

                    dragonHP -= knightDamage
                }

                lastDragon = lastDragon.buff(times * facts.buff)
            }
            Action.ATTACK -> {
                val knightHP = lastKnight.health - timesRemaining * lastDragon.damage
                val turnsBeforeNextCure = turnsToKill(knightDamage, dragonHP) - 1
                val commonTurnsBeforeNextCure = turnsToKill(knightDamage, totalHealth) - 2

                if (knightHP <= 0) timesRemaining--
                if (turnsBeforeNextCure < 1 && commonTurnsBeforeNextCure < 1 && timesRemaining != 0) {
                    throw ImpossibleException()
                }

                val differenceFromLoop = turnsBeforeNextCure - commonTurnsBeforeNextCure
                if (differenceFromLoop > 0) {
                    val turns = Math.min(differenceFromLoop, timesRemaining)
                    dragonHP -= turns * knightDamage
                    timesRemaining -= turns
                } else if (differenceFromLoop < 0) {
                    val turns = Math.min(turnsBeforeNextCure, timesRemaining)
                    dragonHP = totalHealth - knightDamage
                    timesRemaining -= turns

                    if (timesRemaining != 0) curesNeeded++
                }

                if (timesRemaining > 0) {
                    val cycles = (timesRemaining / commonTurnsBeforeNextCure)
                    if (cycles > 0) {
                        timesRemaining -= cycles * commonTurnsBeforeNextCure
                        curesNeeded += cycles
                        dragonHP = totalHealth - knightDamage
                    }

                    if (timesRemaining == 0) curesNeeded--

                    dragonHP -= timesRemaining * knightDamage
                }

                lastKnight = lastKnight.recoverTo(knightHP)
            }
        }

        cures = turn.cures + curesNeeded
        dragon = lastDragon.recoverTo(dragonHP)
        knight = lastKnight
    }
}