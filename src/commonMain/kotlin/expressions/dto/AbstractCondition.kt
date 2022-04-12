package expressions.dto

abstract class AbstractCondition<T>(vararg conditions: AbstractCondition<T>)  {
    protected val conditions: List<AbstractCondition<T>> = conditions.toList()

    internal abstract fun getAllCandidates(): List<Condition<T>>
    internal abstract fun isSatisfied(): Boolean
    internal abstract fun setValue(condition: Condition<T>, value: Boolean)

    abstract override fun toString(): String

    fun or(conditions: AbstractCondition<T>): OrCondition<T> {
        return OrCondition(this, conditions)
    }

    fun and(conditions: AbstractCondition<T>) : AndCondition<T> {
        return AndCondition(this, conditions)
    }

    fun not(): NotCondition<T> {
        return NotCondition(this)
    }
}