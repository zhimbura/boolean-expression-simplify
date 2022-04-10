package expressions.dto

class Condition(val item: Any) : AbstractCondition() {
    private var value: Boolean = false

    override fun getAllCandidates() = listOf(this)

    override fun isSatisfied() = value
    override fun setValue(condition: Condition, value: Boolean) {
        if (this == condition) {
            this.value = value
        }
    }

    override fun toString() = item.toString()

    override fun hashCode() = item.hashCode()
    override fun equals(other: Any?) = other is Condition && other.item == item
}