package org.expressions

import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * Interface objects that can be simplified
 * */
@JsExport
interface ISimplifiedCondition<T> {
    /**
     * Returns all base condition statements
     * */
    @JsName("getAllCandidates")
    fun getAllCandidates(): List<ISimplifiedCondition<T>>

    /**
     * Returns the value set with [setValue] depends on conditions
     * */
    fun isSatisfiedByValue(): Boolean

    /**
     * Sets the value that [isSatisfiedByValue] will return to the base operands
     * @param condition base operand, it is assumed that the value will be set for all operands with the same [hashCode]
     * @param value value to be set
     * */
    fun setValue(condition: ISimplifiedCondition<T>, value: Boolean)

    /**
     * Creates a concatenating operand based on OR
     * */
    fun or(condition: ISimplifiedCondition<T>): ISimplifiedCondition<T>

    /**
     * Creates a concatenating operand based on AND
     * */
    fun and(condition: ISimplifiedCondition<T>): ISimplifiedCondition<T>

    /**
     * Creates an inverting operand
     * */
    fun not(): ISimplifiedCondition<T>
}