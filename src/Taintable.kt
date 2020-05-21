import java.io.Serializable

sealed class Taintable<out T> : Serializable {
    abstract operator fun component1(): T

    class TaintedException : Exception("Attempted to use tainted object without acknowledging the risk!")

    abstract fun get(): T

    companion object {
        fun <T> untainted(value: T): Taintable<T> = Untainted(value)
        fun <T> tainted(value: T): Taintable<T> = Tainted(value)
    }

    class Untainted<out T>(private val value: T) : Taintable<T>() {
        override fun component1(): T = value

        override fun get(): T = value

        override fun toString(): String = value.toString()
    }

    class Tainted<out T>(private val value: T) : Taintable<T>() {
        override fun component1(): T = throw TaintedException()

        override fun get(): T = throw TaintedException()
        fun getDangerous(): T = value

        override fun equals(other: Any?): Boolean = other is Tainted<*> && other.value == this.value
        override fun hashCode(): Int = value.hashCode()

        override fun toString(): String = throw TaintedException()
    }
}
