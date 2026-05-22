package org.example.project.qdcore.parser.walker.funcall

import org.example.project.qdcore.parser.walker.WalkerParser

/**
 * Parser that walks through a function call and produces a [WalkedFunctionCall].
 * @param allowsBody whether the function call allows an indented body argument
 * @see FunctionCallGrammar
 * @see WalkedFunctionCall
 * @see WalkerParser
 */
class FunctionCallWalkerParser(
    source: CharSequence,
    allowsBody: Boolean,
) : WalkerParser<WalkedFunctionCall>(
        source,
        FunctionCallGrammar(allowsBody),
    )
