package br.com.gabryel.round1A.c.model

import br.com.gabryel.round1A.c.turnsToKill

data class RPGChar(val health: Int, val damage: Int, private val from: RPGChar? = null) {

    val initial: RPGChar = from?.initial?: this

    fun cure() = to(initial.health)

    fun to(health: Int) = RPGChar(health, damage, this)

    fun buff(buff: Int) = RPGChar(health, damage + buff, this)

    fun debuff(debuff: Int) = RPGChar(health, maxOf(0, damage - debuff), this)

    fun takeDamage(damageTook: Int) = RPGChar(health - damageTook, damage, this)

    fun turnsToKill(defender: RPGChar) = turnsToKill(damage, defender.health)

    fun canKill(defender: RPGChar) = defender.health <= damage
}

