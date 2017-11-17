package br.com.gabryel.round1A.c

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import java.util.*
import java.util.stream.Stream


class Solver1ACTest {
    @ParameterizedTest(name = "testGoogleInput {index}")
    @ArgumentsSource(value = GoogleProvider::class)
    fun testGoogleInput(input: String, output: String) {
        assertThat(solve(Scanner(input)).toString(), equalTo(output))
    }
}

private object GoogleProvider: ArgumentsProvider {
    override fun provideArguments(context: ExtensionContext?): Stream<Arguments> = Stream.of(
            Arguments.of("11 5 16 5 0 0", "5"),
            Arguments.of("3 1 3 2 2 0", "2"),
            Arguments.of("3 1 3 2 1 0", "IMPOSSIBLE"),
            Arguments.of("2 1 5 1 1 1", "5")
    )
}