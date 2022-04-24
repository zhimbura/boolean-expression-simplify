package org.expressions


/**
 * Interface objects that can be simplified
 * */
interface SimplifiedCondition<T> {
    /**
     * Returns all base condition statements
     * */
    fun getAllCandidates(): List<SimplifiedCondition<T>>

    /**
     * Returns the value set with [setValue] depends on conditions
     * */
    fun isSatisfiedByValue(): Boolean

    /**
     * Sets the value that [isSatisfiedByValue] will return to the base operands
     * @param condition base operand, it is assumed that the value will be set for all operands with the same [hashCode]
     * @param value value to be set
     * */
    fun setValue(condition: SimplifiedCondition<T>, value: Boolean)

    /**
     * Creates a concatenating operand based on OR
     * */
    fun or(conditions: SimplifiedCondition<T>): SimplifiedCondition<T>

    /**
     * Creates a concatenating operand based on AND
     * */
    fun and(conditions: SimplifiedCondition<T>): SimplifiedCondition<T>

    /**
     * Creates an inverting operand
     * */
    fun not(): SimplifiedCondition<T>
}