package br.com.gabryel.round1A.c.model

data class Turn(val facts: BattleFacts,
                val cures: Int = 0,
                val buffs: Int = 0,
                val attacks: Int = 0,
                val debuffs: Int = 0,
                val dragon: RPGChar = facts.initDragon,
                val knight: RPGChar = facts.initKnight,
                val curedInLastTurn: Boolean = false) {

    val turns = cures + attacks + buffs + debuffs

    fun transform(cures: Int = 0, buffs: Int = 0, attacks: Int = 0, debuffs: Int = 0,
                  newDragon: RPGChar = dragon, newKnight: RPGChar = knight,
                  curedLastTurn: Boolean): Turn {
        return Turn(
                cures = this.cures + cures,
                buffs = this.buffs + buffs,
                attacks = this.attacks + attacks,
                debuffs = this.debuffs + debuffs,
                curedInLastTurn = curedLastTurn,
                dragon = newDragon,
                knight = newKnight,
                facts = facts)
    }

    fun act(action: Action, times: Int) = action.repeat(this, times)
}