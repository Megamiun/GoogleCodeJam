package br.com.gabryel.round1A.c

import br.com.gabryel.round1A.c.model.RPGChar
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import java.util.stream.Stream

class TurnsToKillTest {
    @ParameterizedTest(name = "{3}")
    @ArgumentsSource(value = DuelProvider::class)
    fun testTurnsToKill(attacker: RPGChar, defender: RPGChar, turns: Int) {
        assertThat(attacker.turnsToKill(defender), equalTo(turns))
    }
}

private object DuelProvider : ArgumentsProvider {
    private val ATTACKER = RPGChar(3, 3)

    override fun provideArguments(context: ExtensionContext?): Stream<Arguments> = Stream.of(
            Arguments.of(ATTACKER, RPGChar(1, 2), 1, "Test For Less Than One Hit"),
            Arguments.of(ATTACKER, RPGChar(3, 2), 1, "Test For Correct Damage For Kill"),
            Arguments.of(ATTACKER, RPGChar(5, 2), 2, "Test For Intermediate Damage For Kill"),
            Arguments.of(ATTACKER, RPGChar(15, 5), 5, "Test For Multiple Hits")
    )
}