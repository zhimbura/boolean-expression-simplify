package org.expressions.impl

import org.expressions.ISimplifiedCondition
import kotlin.js.JsExport
import kotlin.js.JsName

@JsExport
class Condition<T>(
    @property:JsName("item")
    val item: T
) : AbstractCondition<T>() {
    private var value: Boolean = false

    override fun getAllCandidates() = listOf(this)

    override fun isSatisfiedByValue() = value

    override fun setValue(condition: ISimplifiedCondition<T>, value: Boolean) {
        if (this == condition) {
            this.value = value
        }
    }

    override fun toString() = item.toString()

    override fun hashCode() = item.hashCode()
    override fun equals(other: Any?) = other is Condition<*> && other.item == item
}