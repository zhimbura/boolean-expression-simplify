package expressions.dto

class AlwaysFalseCondition<T>: AbstractCondition<T>() {
    override fun getAllCandidates() = emptyList<Condition<T>>()

    override fun isSatisfied() = false

    override fun setValue(condition: Condition<T>, value: Boolean) {
        throw Exception("Can't set value for const condition")
    }

    override fun toString() = "false"
}