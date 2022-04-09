package expressions.dto

class Condition(val item: Any) : AbstractCondition() {
    internal val value: Boolean = false

    override fun getAllCandidates() = listOf(this)

    override fun isSatisfied() = value

    override fun toString() = item.toString()
}