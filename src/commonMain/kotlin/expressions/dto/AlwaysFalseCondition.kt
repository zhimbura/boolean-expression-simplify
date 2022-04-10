package expressions.dto

class AlwaysFalseCondition: AbstractCondition() {
    override fun getAllCandidates() = emptyList<Condition>()

    override fun isSatisfied() = false

    override fun setValue(condition: Condition, value: Boolean) {
        throw Exception("Can't set value for const condition")
    }

    override fun toString() = "false"
}