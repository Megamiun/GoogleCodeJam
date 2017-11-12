package br.com.gabryel.round1A.c

fun turnsToKill(attack: Int, health: Int) = Math.ceil(health.toDouble() / attack).toInt()