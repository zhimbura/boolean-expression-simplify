package org.expressions.impl

import org.expressions.ISimplifiedCondition
import kotlin.js.JsExport

@JsExport
class NotCondition<T>(condition: ISimplifiedCondition<T>) : AbstractCondition<T>(condition) {

    override fun getAllCandidates() = conditions.flatMap { it.getAllCandidates() }

    override fun isSatisfiedByValue() = !this.conditions.any { it.isSatisfiedByValue() }
    override fun setValue(condition: ISimplifiedCondition<T>, value: Boolean) {
        for (c in conditions) {
            c.setValue(condition, value)
        }
    }

    override fun toString(): String {
        return "!${conditions[0]}"
    }
}