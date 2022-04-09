package tests

import expressions.SimplerBooleanExpression
import expressions.dto.AndCondition
import expressions.dto.OrCondition
import kotlin.test.Test
import kotlin.test.assertEquals

class TestSimplify {

    @Test
    fun testSimplifyNothing() {
        // A && (B || C)
        val condition = AndCondition(
            "A",
            OrCondition(
                "B",
                "C"
            )
        )
        val simpler = SimplerBooleanExpression()
        val simpled = simpler.simplify(condition)
        assertEquals(condition, simpled)
    }

    @Test
    fun testCombine() {
        val simpler = SimplerBooleanExpression()
        simpler.permutation(listOf(0, 0, 1), arrayListOf())
    }

}