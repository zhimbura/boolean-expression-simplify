package expressions.dto

abstract class AbstractCondition(vararg conditions: Any)  {
    protected val conditions: List<AbstractCondition> = conditions.map {
        if (it is AbstractCondition) it
        else Condition(it)
    }

    internal abstract fun getAllCandidates(): List<Condition>
    internal abstract fun isSatisfied(): Boolean
    internal abstract fun setValue(condition: Condition, value: Boolean)

    abstract override fun toString(): String

    fun or(conditions: AbstractCondition): OrCondition {
        return OrCondition(this, conditions)
    }

    fun and(conditions: AbstractCondition) : AndCondition {
        return AndCondition(this, conditions)
    }

    fun not(): NotCondition {
        return NotCondition(this)
    }
}