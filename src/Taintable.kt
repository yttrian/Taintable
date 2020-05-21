class Taintable<T>(private val value: T, private var tainted: Boolean = false) {
    companion object {
        class TaintedException : Exception("Attempted to use tainted object in a secure context!")
    }

    val isTainted: Boolean
        get() = tainted

    fun getInsecure(): T = value

    fun get(): T = if (tainted) throw TaintedException() else value

    fun orElse(other: T): T = if (tainted) other else value

    fun taint() {
        tainted = true
    }

    override fun toString(): String = if (tainted) throw TaintedException() else value.toString()
}

fun <T> T.toTaintable(taint: Boolean = false) = Taintable(this, taint)