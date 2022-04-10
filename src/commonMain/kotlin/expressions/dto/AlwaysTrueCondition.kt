package expressions.dto

class AlwaysTrueCondition: AbstractCondition() {
    override fun getAllCandidates() = emptyList<Condition>()

    override fun isSatisfied() = true

    override fun setValue(condition: Condition, value: Boolean) {
        throw Exception("Can't set value for const condition")
    }

    override fun toString() = "true"
}