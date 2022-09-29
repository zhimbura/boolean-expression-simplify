package org.expressions.impl

import org.expressions.ISimplifiedCondition
import kotlin.js.JsExport

@JsExport
class NotCondition<T>(vararg conditions: ISimplifiedCondition<T>) : AbstractCondition<T>(*conditions) {

    override fun getAllCandidates() = conditions.flatMap { it.getAllCandidates() }

    override fun isSatisfiedByValue() = !this.conditions.any { it.isSatisfiedByValue() }
    override fun setValue(condition: ISimplifiedCondition<T>, value: Boolean) {
        for (c in conditions) {
            c.setValue(condition, value)
        }
    }

    override fun toString(): String {
        return if (conditions.size == 1) {
            "!${conditions[0]}"
        } else {
            "!(${conditions.map { it }})"
        }
    }
}