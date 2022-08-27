package org.expressions.impl.parser

internal interface Token {
    object OR : Token, IOperator
    object AND : Token, IOperator
    object NOT : Token
    object LBRACE : Token
    object RBRACE : Token
    data class StringValue(val value: String) : Token
    sealed interface IOperator
}

internal class CharReader(reader: String) {
    private var eof = false
    private var nextChar: Char? = null
    private var reader: String = reader
        .replace("&&", "&")
        .replace("||", "|")
        .replace(" ", "")

    private fun advance() {
        if (eof) return
        when (val c = reader.firstOrNull()) {
            null -> eof = true
            else -> {
                reader = reader.substring(1 until reader.length)
                nextChar = c
            }
        }
    }

    fun peekNext(): Char? {
        if (nextChar == null) advance()
        return nextChar?.takeIf { !eof }
    }

    fun takeNext() = peekNext().apply { nextChar = null }
}

internal class Lexer(reader: String) {
    private val charReader = CharReader(reader)

    private val tokenMap = hashMapOf<Char, (Char) -> Token> (
        '|' to { Token.OR },
        '&' to { Token.AND },
        '(' to { Token.LBRACE },
        ')' to { Token.RBRACE },
        '!' to { Token.NOT }
        // TODO Implement not
    )

    fun nextToken(): Token? {
        val c: Char = charReader.takeNext() ?: return null
        return tokenMap[c]?.invoke(c) ?: readStringToken(c)
    }

    fun nextIsBrakedSymbol(): Boolean {
        return charReader.peekNext() in tokenMap.keys
    }


    private fun readStringToken(firstChar: Char): Token {
        val result = StringBuilder().apply {
            append(firstChar)
        }
        while (true) {
            val c = charReader.peekNext()
            if (c == null || c in tokenMap.keys) break
            result.append(charReader.takeNext())
        }
        return Token.StringValue(result.toString())
    }

}
