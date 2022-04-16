package tests

import org.expressions.SimplerBooleanExpression
import org.expressions.impl.*
import kotlin.test.*

class TestSimplify {

    @Test
    fun testSimplifyNothing() {
        // A && (B || C) -> A && (B || C)
        val condition = AndCondition(
            Condition("A"),
            OrCondition(
                Condition("B"),
                Condition("C"),
            )
        )
        val simpler = SimplerBooleanExpression()
        val simpled = simpler.simplify(condition)
        assertEquals(condition, simpled)
    }

    @Test
    fun testSimplifyCase1() {
        // A && (B || A) -> A
        val condition = AndCondition(
            Condition("A"),
            OrCondition(
                Condition("B"),
                Condition("A"),
            )
        )
        val simpler = SimplerBooleanExpression()
        val simpled = simpler.simplify(condition)
        assertNotNull(simpled)
        assertEquals(1, simpled.getAllCandidates().size)
        assertEquals("A", simpled.toString())
    }

    @Test
    fun testSimplifyCase2() {
        // A && A -> A
        val condition = AndCondition(
            Condition("A"),
            Condition("A"),
        )
        val simpler = SimplerBooleanExpression()
        val simpled = simpler.simplify(condition)
        assertNotNull(simpled)
        assertEquals(1, simpled.getAllCandidates().size)
        assertEquals("A", simpled.toString())
    }

    @Test
    fun testSimplifyCase3() {
        // A && !A -> null
        val condition = AndCondition(
            Condition("A"),
            NotCondition(Condition("A"))
        )
        val simpler = SimplerBooleanExpression()
        val simpled = simpler.simplify(condition)
        assertNotNull(simpled)
        assertTrue(simpled is AlwaysFalseCondition)
    }

    @Test
    fun testSimplifyCase4() {
        // A || !A -> true
        val condition = OrCondition(
            Condition("A"),
            NotCondition(Condition("A"))
        )
        val simpler = SimplerBooleanExpression()
        val simpled = simpler.simplify(condition)
        assertNotNull(simpled)
        assertTrue(simpled is AlwaysTrueCondition)
    }

    @Test
    fun testBooleanTable() {
        // A && (B || C)
        val condition = AndCondition(
            Condition("A"),
            OrCondition(
                Condition("B"),
                Condition("C")
            )
        )
        val conditions = condition.getAllCandidates()
        val simpler = SimplerBooleanExpression()
        val table = simpler.getBooleanTable(condition, conditions)
        assertEquals(8, table.size)
        val correctTable = mapOf(
            "000" to 0,
            "001" to 0,
            "010" to 0,
            "011" to 0,
            "100" to 0,
            "101" to 1,
            "110" to 1,
            "111" to 1
        )
        for ((key, value) in table) {
            val row = key.joinToString("")
            assertEquals(correctTable[row], value)
        }

    }

}