package org.expressions.impl

import org.expressions.ISimplifiedCondition
import kotlin.js.JsExport
import kotlin.js.JsName

@JsExport
abstract class AbstractCondition<T>(vararg conditions: ISimplifiedCondition<T>) : ISimplifiedCondition<T> {

    @JsName("conditions")
    val conditions: List<ISimplifiedCondition<T>> = conditions.toList()
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