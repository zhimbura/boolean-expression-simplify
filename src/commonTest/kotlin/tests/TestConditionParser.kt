package tests

import org.expressions.ISimplifiedCondition
import org.expressions.SimplerBooleanExpression
import org.expressions.impl.AndCondition
import org.expressions.impl.Condition
import org.expressions.impl.NotCondition
import org.expressions.impl.OrCondition
import org.expressions.impl.parser.ConditionParser
import org.expressions.impl.parser.Lexer
import org.expressions.impl.parser.Token
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals

class TestConditionParser {

    @Test
    fun testLexer() {
        val lexer = Lexer("AAA && (BBB || AAA)")
        assertEquals(Token.StringValue("AAA"), lexer.nextToken())
        assertEquals(Token.AND, lexer.nextToken())
        assertEquals(Token.LBRACE, lexer.nextToken())
        assertEquals(Token.StringValue("BBB"), lexer.nextToken())
        assertEquals(Token.OR, lexer.nextToken())
        assertEquals(Token.StringValue("AAA"), lexer.nextToken())
        assertEquals(Token.RBRACE, lexer.nextToken())
        assertEquals(null, lexer.nextToken())
    }

    @Test
    @Ignore
    fun t() {
        val d = "(((((((((((((bb8eaa5c) && (39b92386)) && (3955f59e)) && (700d7963)) && (803f287e)) && (8364e022)) && (801fa354)) && (87c491e6)) && (8650588c)) && (b898229e)) && (9671ac2c)) && (deffc742)) && (defffe26)) && (40bb349)"
        val condition = ConditionParser().parse(d)
        val simplerBooleanExpression = SimplerBooleanExpression()
        val simpl = simplerBooleanExpression.simplify(condition)
        println("old $d\nnew ${simpl.toString()}")
    }

    @Test
    fun testParser() {
        case(
            input = "A && B",
            condition = AndCondition(
                Condition("A"),
                Condition("B"),
            )
        )
        case(
            input = "A || B",
            condition = OrCondition(
                Condition("A"),
                Condition("B"),
            )
        )
        case(
            input = "A && B && C",
            condition = AndCondition(
                AndCondition(
                    Condition("A"),
                    Condition("B")
                ),
                Condition("C")
            )
        )
        case(
            input = "A && B || C",
            condition = OrCondition(
                AndCondition(
                    Condition("A"),
                    Condition("B")
                ),
                Condition("C")
            )
        )
        case(
            input = "A && (B || A)",
            condition = AndCondition(
                Condition("A"),
                OrCondition(
                    Condition("B"),
                    Condition("A")
                )
            )
        )
        case(
            input = "(B || A) && A",
            condition = AndCondition(
                OrCondition(
                    Condition("B"),
                    Condition("A"),
                ),
                Condition("A")
            )
        )
        case(
            input = "!A",
            condition = NotCondition(
                Condition("A")
            )
        )
        case(
            input = "!A && !B",
            condition = AndCondition(
                NotCondition(Condition("A")),
                NotCondition(Condition("B"))
            )
        )
        case(
            input = "!(A && B)",
            condition = NotCondition(
                AndCondition(
                    Condition("A"),
                    Condition("B"),
                )
            )
        )
        case(
            input = "!(A) && (B)",
            condition = AndCondition(
                NotCondition(
                    Condition("A"),
                ),
                Condition("B")
            )
        )
        case(
            input = "!(A && B) && (B)",
            condition = AndCondition(
                NotCondition(
                    AndCondition(
                        Condition("A"),
                        Condition("B"),
                    )
                ),
                Condition("B")
            )
        )
        case(
            input = "!((A)&&(B))",
            condition = NotCondition(
                AndCondition(
                    Condition("A"),
                    Condition("B")
                )
            )
        )
    }

    fun case(
        input: String,
        condition: ISimplifiedCondition<String>
    ) {
        val parser = ConditionParser()
        assertEquals(
            condition.toString(),
            parser.parse(input).toString(),
            "Parse expression should be equals toString result"
        )

    }
}