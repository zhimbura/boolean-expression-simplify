package tests

import org.expressions.conditionToJson
import org.expressions.impl.parser.ConditionParser
import kotlin.test.Test
import kotlin.test.assertEquals

class TestConditionToJson {

    @Test
    fun testConditionTOJson() {
        val condition = ConditionParser().parse("!(f0291947)&&(f12412)")
        val actual = conditionToJson(condition)
        // language=json
        val expected = """
            {
              "type": "AndCondition",
              "conditions": [
                {
                  "type": "NotCondition",
                  "conditions": [
                    {
                      "type": "Condition",
                      "condition": "f0291947"
                    }
                  ]
                },
                {
                  "type": "Condition",
                  "condition": "f12412"
                }
              ]
            }
        """.trimIndent()
        assertEquals(expected, JSON.stringify(actual, null, 2))
    }
}