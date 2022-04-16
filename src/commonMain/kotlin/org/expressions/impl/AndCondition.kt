package org.expressions.impl

import org.expressions.SimplifiedCondition

class AndCondition<T>(vararg conditions: SimplifiedCondition<T>) : AbstractCondition<T>(*conditions) {

    override fun getAllCandidates() = conditions.flatMap { it.getAllCandidates() }

    override fun isSatisfiedByValue() = conditions.all { it.isSatisfiedByValue() }
    override fun setValue(condition: SimplifiedCondition<T>, value: Boolean) {
        for (c in this.conditions) {
            c.setValue(condition, value)
        }
    }

    override fun toString() = conditions.joinToString(" && ", "(", ")")
}