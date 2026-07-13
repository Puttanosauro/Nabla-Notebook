package com.github.h0tk3y.betterParse.lexer

import com.github.h0tk3y.betterParse.parser.*

// Vendored modification: was `@OptionalExpectation public expect annotation class` whose only
// actual was a JVM typealias to org.intellij.lang.annotations.Language (IDE regex highlighting).
// Made a plain annotation so the vendored sources contain no expect/actual declarations at all.
@Retention(AnnotationRetention.BINARY)
public annotation class Language(val value: String, val prefix: String, val suffix: String)

/**
 * Represents a basic detectable part of the input that may be [ignored] during parsing.
 * Parses to [TokenMatch].
 * The [name] only provides additional information.
 */
public abstract class Token(public var name: String? = null, public val ignored: Boolean) : Parser<TokenMatch> {

    public abstract fun match(input: CharSequence, fromIndex: Int): Int

    override fun tryParse(tokens: TokenMatchesSequence, fromPosition: Int): ParseResult<TokenMatch> =
        tryParseImpl(tokens, fromPosition)

    private tailrec fun tryParseImpl(tokens: TokenMatchesSequence, fromPosition: Int): ParseResult<TokenMatch> {
        val tokenMatch = tokens[fromPosition] ?: return UnexpectedEof(this)
        return when {
            tokenMatch.type == this -> tokenMatch
            tokenMatch.type == noneMatched -> NoMatchingToken(tokenMatch)
            tokenMatch.type.ignored -> tryParseImpl(tokens, fromPosition + 1)
            else -> MismatchedToken(this, tokenMatch)
        }
    }
}

/** Token type indicating that there was no [Token] found to be matched by a [Tokenizer]. */
public val noneMatched: Token = object : Token("no token matched", false) {
    override fun match(input: CharSequence, fromIndex: Int): Int = 0
    override fun toString(): String = "noneMatched!"
}