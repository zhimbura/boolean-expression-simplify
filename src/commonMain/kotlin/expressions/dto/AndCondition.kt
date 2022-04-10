package expressions.dto

class AndCondition(vararg conditions: Any) : AbstractCondition(*conditions) {

    override fun getAllCandidates() = conditions.flatMap { it.getAllCandidates() }

    override fun isSatisfied() = conditions.all { it.isSatisfied() }
    override fun setValue(condition: Condition, value: Boolean) {
        for (c in this.conditions) {
            c.setValue(condition, value)
        }
    }

    override fun toString() = conditions.joinToString(" && ", "(", ")")
}