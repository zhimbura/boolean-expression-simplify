package org.expressions.impl

import org.expressions.ISimplifiedCondition

class AndCondition<T>(vararg conditions: ISimplifiedCondition<T>) : AbstractCondition<T>(*conditions) {

    override fun getAllCandidates() = conditions.flatMap { it.getAllCandidates() }

    override fun isSatisfiedByValue() = conditions.all { it.isSatisfiedByValue() }
    override fun setValue(condition: ISimplifiedCondition<T>, value: Boolean) {
        for (c in this.conditions) {
            c.setValue(condition, value)
        }
    }

    override fun toString() = conditions.joinToString(" && ", "(", ")")
}