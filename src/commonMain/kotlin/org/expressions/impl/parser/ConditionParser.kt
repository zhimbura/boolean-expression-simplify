package org.expressions.impl.parser

import org.expressions.IConditionParser
import org.expressions.ISimplifiedCondition
import org.expressions.impl.Condition
import org.expressions.impl.NotCondition
import kotlin.js.JsExport

class ParseException : Exception("Wrong expression.")

@JsExport
class ConditionParser : IConditionParser {
    override fun parse(expression: String): ISimplifiedCondition<String> {
        val lexer = Lexer(expression)
        return parseToken(lexer)
    }

    private fun parseToken(lexer: Lexer, parentToken:Token? = null): ISimplifiedCondition<String> {
        var token: Token?
        var condition: ISimplifiedCondition<String>? = null
        var operator: Token.IOperator? = null
        fun setCondition(cb: () -> ISimplifiedCondition<String>) {
            condition = cb()
            operator = null
        }
        do {
            token = lexer.nextToken()
            when (token) {
                is Token.IOperator -> operator = token
                is Token.StringValue -> setCondition {
                    val nextCondition = Condition(token.value)
                    operator.joinElse(condition, nextCondition) { nextCondition }
                }
                is Token.NOT -> setCondition {
                    val nextCondition = when {
                        lexer.nextIsBrakedSymbol() -> NotCondition(parseToken(lexer, Token.NOT))
                        else -> when (val nextToken = lexer.nextToken()) {
                            is Token.StringValue -> NotCondition(Condition(nextToken.value))
                            else -> throw ParseException()
                        }
                    }
                    operator.joinElse(condition, nextCondition) { nextCondition }
                }
                Token.LBRACE -> {
                    setCondition {
                        val nextCondition = parseToken(lexer)
                        operator.joinElse(condition, nextCondition) { nextCondition }
                    }
                    if (parentToken is Token.NOT) {
                        return condition ?: throw ParseException()
                    }
                }
                Token.RBRACE -> return condition ?: throw ParseException()
            }
        } while (token != null)
        return condition ?: throw ParseException()
    }

    private fun checkAndJoin(
        left: ISimplifiedCondition<String>?,
        operator: Token.IOperator?,
        right: ISimplifiedCondition<String>?
    ): ISimplifiedCondition<String> {
        require(left != null)
        require(right != null)
        require(operator != null)
        return when (operator) {
            Token.OR -> left.or(right)
            Token.AND -> left.and(right)
        }
    }

    private fun Token.IOperator?.joinElse(
        left: ISimplifiedCondition<String>?,
        right: ISimplifiedCondition<String>?,
        elseCallBack: () -> ISimplifiedCondition<String>
    ): ISimplifiedCondition<String> {
        return this?.let { checkAndJoin(left, this, right) } ?: elseCallBack()
    }


}