package br.com.gabryel.round1A.c

import br.com.gabryel.round1A.c.model.BattleFacts
import br.com.gabryel.round1A.c.model.RPGChar
import br.com.gabryel.round1A.c.model.Turn
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import java.util.stream.Stream

class GetNumberOfCuresTest {
    @ParameterizedTest(name = "{2}")
    @ArgumentsSource(value = TurnProvider::class)
    fun testGoogleInput(turn: Turn, cures: Int?) {
        assertThat(getNumberOfCures(turn), equalTo(cures))
    }
}

private object TurnProvider : ArgumentsProvider {
    private val INITIAL_ATTACKER = RPGChar(100, 10)

    override fun provideArguments(context: ExtensionContext?): Stream<Arguments> = Stream.of(
            Arguments.of(turnWith(100, 100), null, "Dragon OHKO"),
            Arguments.of(turnWith(21, 51), null, "Dragon THKO"),
            Arguments.of(turnWith(31, 51, buff = 10), null, "Buff Is Not Enough"),
            Arguments.of(turnWith(31, 51, buff = 100), 0, "Buff Over Limit Then Attack"),
            Arguments.of(turnWith(60, 49), 3, "Alternating Attack And Cure, Correct Damage"),
            Arguments.of(turnWith(70, 49), 4, "Alternating Attack And Cure, Incorrect Damage"),
            Arguments.of(turnWith(60, 49, dragonHP = 51), 4, "Start Damaged")
    )

    fun turnWith(knightHP: Int, knightDamage: Int, dragonHP: Int = INITIAL_ATTACKER.health, buff: Int = 0): Turn {
        val defender = RPGChar(knightHP, knightDamage)

        return Turn(facts = BattleFacts(INITIAL_ATTACKER, defender, buff = buff),
                dragon = INITIAL_ATTACKER.to(dragonHP),
                knight = defender)
    }
}