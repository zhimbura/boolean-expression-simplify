package tests

import org.expressions.impl.AndCondition
import org.expressions.impl.Condition
import org.expressions.impl.OrCondition
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