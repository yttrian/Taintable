class TaintedString(private val value: String) {
    /**
     * Accept the risk of a tainted string and use it
     */
    fun risk(): String = value

    operator fun plus(other: String): TaintedString = TaintedString(value + other)
    operator fun plus(other: TaintedString): TaintedString = TaintedString(value + other.value)

    override fun toString(): String = throw TaintedException()
}