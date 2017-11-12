package domain

import org.junit.Test

import org.junit.Assert.*

class ParseDoubleTest {
    @Test
    fun `parseDouble should transform spanish format Strings into Double`() {
        val strings = listOf(
                "1",
                "12,2",
                "1.312,54",
                "99,999",
                "1.000.000,99"
        )

        val doubles = listOf(
                1.0,
                12.2,
                1_312.54,
                99.999,
                1_000_000.99
        )

        strings
                .map(String::parseDouble)
                .mapIndexed { index, d ->
                    assertEquals(d, doubles[index], 0.001)
                }
    }

}