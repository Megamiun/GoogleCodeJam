package br.com.gabryel.round1A.c.model

import br.com.gabryel.round1A.c.turnsToKill

data class RPGChar(val health: Int, val damage: Int) {
    fun recoverTo(health: Int) = RPGChar(health, this.damage)

    fun buff(buff: Int) = RPGChar(health, damage + buff)

    fun debuff(debuff: Int) = RPGChar(health, maxOf(0, damage - debuff))

    fun turnsToKill(defender: RPGChar) = turnsToKill(damage, defender.health)
}