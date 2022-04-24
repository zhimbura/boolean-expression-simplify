package org.expressions.impl

import org.expressions.SimplifiedCondition

class OrCondition<T>(vararg conditions: SimplifiedCondition<T>) : AbstractCondition<T>(*conditions) {

    override fun getAllCandidates() = conditions.flatMap { it.getAllCandidates() }

    override fun isSatisfiedByValue() = this.conditions.any { it.isSatisfiedByValue() }
    override fun setValue(condition: SimplifiedCondition<T>, value: Boolean) {
        for (c in conditions) {
            c.setValue(condition, value)
        }
    }

    override fun toString() = conditions.joinToString(" || ", "(", ")")
}