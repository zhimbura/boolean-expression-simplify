package org.expressions.impl

import org.expressions.ISimplifiedCondition

class AlwaysFalseCondition<T> : AbstractCondition<T>() {
    override fun getAllCandidates() = emptyList<AbstractCondition<T>>()

    override fun isSatisfiedByValue() = false

    override fun setValue(condition: ISimplifiedCondition<T>, value: Boolean) {
        throw Exception("Can't set value for const condition")
    }

    override fun toString() = "false"
}