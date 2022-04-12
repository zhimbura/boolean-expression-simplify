package expressions.dto

class NotCondition<T>(vararg conditions: AbstractCondition<T>) : AbstractCondition<T>(*conditions) {

    override fun getAllCandidates() = conditions.flatMap { it.getAllCandidates() }

    override fun isSatisfied() = !this.conditions.any { it.isSatisfied() }
    override fun setValue(condition: Condition<T>, value: Boolean) {
        for (c in conditions) {
            c.setValue(condition, value)
        }
    }

    override fun toString(): String {
        return if (conditions.size == 1) {
            "!${conditions[0]}"
        } else {
            "!(${conditions.map { it }})"
        }
    }
}