package org.expressions.impl

import org.expressions.ISimplifiedCondition
import kotlin.js.JsExport

@JsExport
class AlwaysTrueCondition<T> : AbstractCondition<T>() {
    override fun getAllCandidates() = emptyList<AbstractCondition<T>>()

    override fun isSatisfiedByValue() = true

    override fun setValue(condition: ISimplifiedCondition<T>, value: Boolean) {
        throw Exception("Can't set value for const condition")
    }

    override fun toString() = "true"
}