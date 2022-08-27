package tests

import org.expressions.ISimplifiedCondition
import org.expressions.impl.AndCondition
import org.expressions.impl.Condition
import org.expressions.impl.NotCondition
import org.expressions.impl.OrCondition
import org.expressions.impl.parser.ConditionParser
import org.expressions.impl.parser.Lexer
import org.expressions.impl.parser.Token
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