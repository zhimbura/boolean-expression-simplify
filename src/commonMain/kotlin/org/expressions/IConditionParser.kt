package org.expressions

import kotlin.js.JsExport
import kotlin.js.JsName

@JsExport
interface IConditionParser {
    @JsName("parse")
    fun parse(expression: String): ISimplifiedCondition<String>
}