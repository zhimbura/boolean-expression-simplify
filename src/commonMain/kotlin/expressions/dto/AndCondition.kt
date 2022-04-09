package expressions.dto

class AndCondition(vararg conditions: Any) : AbstractCondition(*conditions) {

    override fun getAllCandidates() = conditions.flatMap { it.getAllCandidates() }

    override fun isSatisfied() = conditions.all { it.isSatisfied() }

    override fun toString() = conditions.joinToString(" && ", "(", ")")
}