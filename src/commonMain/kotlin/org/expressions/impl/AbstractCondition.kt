package org.expressions.impl

import org.expressions.ISimplifiedCondition

abstract class AbstractCondition<T>(vararg conditions: ISimplifiedCondition<T>) : ISimplifiedCondition<T> {
    protected val conditions: List<ISimplifiedCondition<T>> = conditions.toList()



    abstract override fun toString(): String

    override fun or(condition: ISimplifiedCondition<T>): ISimplifiedCondition<T> {
        return OrCondition(this, condition)
    }

    override fun and(condition: ISimplifiedCondition<T>): ISimplifiedCondition<T> {
        return AndCondition(this, condition)
    }

    override fun not(): ISimplifiedCondition<T> {
        return NotCondition(this)
    }
}