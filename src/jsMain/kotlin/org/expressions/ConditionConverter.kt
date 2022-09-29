package org.expressions

import org.expressions.impl.*

@JsExport
@JsName("conditionToJson")
fun conditionToJson(condition: ISimplifiedCondition<String>): Any {
    return JSON.parse(conditionToString(condition))
}

private fun conditionToString(condition: ISimplifiedCondition<String>): String {
    return when (condition) {
        is AlwaysTrueCondition -> "true"
        is AlwaysFalseCondition -> "false"
        is AndCondition -> {
            val prefix = """{"type":"AndCondition","conditions":["""
            val postfix = """]}"""
            condition.conditions.joinToString(", ", prefix, postfix) { conditionToString(it) }
        }
        is OrCondition -> {
            val prefix = """{"type":"OrCondition","conditions":["""
            val postfix = """]}"""
            condition.conditions.joinToString(", ", prefix, postfix) { conditionToString(it) }
        }
        is NotCondition -> {
            val prefix = """{"type":"NotCondition","conditions":["""
            val postfix = """]}"""
            condition.conditions.joinToString(", ", prefix, postfix) { conditionToString(it) }
        }
        is Condition -> {
            val prefix = """{"type":"Condition","condition":"""
            val postfix = """}"""
            """$prefix"${condition.item}"$postfix"""
        }
        else -> throw Exception("Error convert")
    }
}