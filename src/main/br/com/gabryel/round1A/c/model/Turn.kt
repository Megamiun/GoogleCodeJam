package br.com.gabryel.round1A.c.model

open class Turn(val facts: BattleFacts) {
    open val cures: Int = 0
    open val buffs: Int = 0
    open val attacks: Int = 0
    open val debuffs: Int = 0

    val turns: Int by lazy { cures + attacks + buffs + debuffs }

    open val dragon: RPGChar = facts.initDragon
    open val knight: RPGChar = facts.initKnight

    fun debuff(times: Int = 1) = turn(times, Action.DEBUFF)

    fun attack(times: Int = 1) = turn(times, Action.ATTACK)

    fun buff(times: Int = 1) = turn(times, Action.BUFF)

    private fun turn(times: Int, action: Action): Turn {
        return if (times == 0) this
        else NextTurn(this, action, times)
    }
}