import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.assertThrows

internal class TaintedStringTest {
    @Test
    fun taintingPlus() {
        val tainted = TaintedString("Hello")
        val untainted = "World"

        assert((tainted + untainted).risk() == "HelloWorld")
        assert((tainted + tainted).risk() == "HelloHello")

        assertThrows<TaintedException> {
            println("$tainted $untainted")
        }

        assertThrows<TaintedException> {
            println(tainted)
        }

        assertThrows<TaintedException> {
            println(tainted + untainted)
        }

        assertDoesNotThrow {
            println(tainted.risk())
        }

        assertDoesNotThrow {
            println(untainted + untainted)
        }
    }
}