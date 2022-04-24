package org.expressions.impl

import org.expressions.SimplifiedCondition

abstract class AbstractCondition<T>(vararg conditions: SimplifiedCondition<T>) : SimplifiedCondition<T> {
    protected val conditions: List<SimplifiedCondition<T>> = conditions.toList()

    abstract override fun toString(): String

    override fun or(conditions: SimplifiedCondition<T>): SimplifiedCondition<T> {
        return OrCondition(this, conditions)
    }

    override fun and(conditions: SimplifiedCondition<T>): SimplifiedCondition<T> {
        return AndCondition(this, conditions)
    }

    override fun not(): SimplifiedCondition<T> {
        return NotCondition(this)
    }
}