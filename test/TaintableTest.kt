import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class TaintableTest {
    @Test
    fun acceptResponsibility() {
        val result = when (val tainted = Taintable.tainted(true)) {
            is Taintable.Tainted -> tainted.getDangerous()
            else -> false
        }

        assert(result)
    }

    @Test
    fun calmlyHandleUntainted() {
        val hello = Taintable.untainted("Hello")

        assert("$hello world!" == "Hello world!")
    }

    @Test
    fun throwsOnDanger() {
        val tainted = Taintable.tainted("evil")

        assertThrows<TaintedException> {
            println("Hello $tainted")
        }
    }

    @Test
    fun ignoresOnSafety() {
        val untainted = Taintable.untainted("happy")

        assertDoesNotThrow {
            println("Hello $untainted")
        }
    }
}