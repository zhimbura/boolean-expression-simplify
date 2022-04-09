package tests

import expressions.dto.AndCondition
import expressions.dto.Condition
import expressions.dto.OrCondition
import kotlin.test.Test
import kotlin.test.assertEquals

class TestToString {

    @Test
    fun testToStringByConstructor() {
        val condition = AndCondition(
            Condition("A"),
            OrCondition(
                Condition("B"),
                Condition("C")
            )
        )
        assertEquals("(A && (B || C))", condition.toString())
    }

    @Test
    fun testToStringByMethods() {
        val condition = Condition("A").and(Condition("B").or(Condition("C")))
        assertEquals("(A && (B || C))", condition.toString())
    }
}