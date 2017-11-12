package br.com.gabryel.round1C.a

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.closeTo
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import java.util.*
import java.util.stream.Stream

class Solver1CATest {
    @ParameterizedTest(name = "testGoogleInput {index}")
    @ArgumentsSource(value = Provider::class)
    fun testGoogleInput(input: String, output: Double) {
        assertThat(solve(Scanner(input)), closeTo(output, Math.pow(10.0, -6.0)))
    }
}

private object Provider: ArgumentsProvider {
    override fun provideArguments(context: ExtensionContext?): Stream<Arguments> = Stream.of(
            Arguments.of("2 1\n 100 20\n 200 10", 138230.076757951),
            Arguments.of("2 2\n 100 20\n 200 10", 150796.447372310),
            Arguments.of("3 2\n 100 10\n 100 10\n 100 10", 43982.297150257),
            Arguments.of("4 2\n 9 3\n 7 1\n10 1\n 8 4", 625.176938064)
    )
}