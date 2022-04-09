package expressions.dto

class NotCondition(vararg conditions: Any) : AbstractCondition(*conditions) {

    override fun getAllCandidates() = conditions.flatMap { it.getAllCandidates() }

    override fun isSatisfied() = !this.conditions.any { it.isSatisfied() }

    override fun toString(): String {
        return if (conditions.size == 1) {
            "!${conditions[0]}"
        } else {
            "!(${conditions.map { it }})"
        }
    }
}