package br.com.gabryel.round1A.c.model

data class Turn(val facts: BattleFacts,
                val cures: Int = 0,
                val buffs: Int = 0,
                val attacks: Int = 0,
                val debuffs: Int = 0,
                val dragon: RPGChar = facts.initDragon,
                val knight: RPGChar = facts.initKnight) {

    val turns = cures + attacks + buffs + debuffs

    fun add(cures: Int = 0, buffs: Int = 0, attacks: Int = 0, debuffs: Int = 0,
            newDragon: RPGChar = dragon, newKnight: RPGChar = knight): Turn {
        return Turn(
                cures = this.cures + cures,
                buffs = this.buffs + buffs,
                attacks = this.attacks + attacks,
                debuffs = this.debuffs + debuffs,
                dragon = newDragon,
                knight = newKnight,
                facts = facts)
    }
}