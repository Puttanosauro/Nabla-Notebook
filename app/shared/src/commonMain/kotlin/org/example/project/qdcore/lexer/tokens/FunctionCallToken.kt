package org.example.project.qdcore.lexer.tokens

import org.example.project.qdcore.lexer.Token
import org.example.project.qdcore.lexer.TokenData
import org.example.project.qdcore.parser.walker.WalkerParsingResult
import org.example.project.qdcore.parser.walker.funcall.WalkedFunctionCall
import org.example.project.qdcore.visitor.token.TokenVisitor

/**
 * A function call token, produced by the lexer's walker subsystem.
 * This is a custom Quarkdown element, and is both a block and inline node.
 *
 * Example:
 * ```
 * .function {arg1} {arg2}
 *     body
 * ```
 * The `body` argument is supported only when used as a block.
 * @param isBlock whether the function call is a block (opposite: inline)
 * @param walkerResult the result of the walker parsing, containing the structured [WalkedFunctionCall]
 * @see org.example.project.qdcore.ast.quarkdown.FunctionCallNode
 */
class FunctionCallToken(
    data: TokenData,
    val isBlock: Boolean,
    val walkerResult: WalkerParsingResult<WalkedFunctionCall>,
) : Token(data) {
    override fun <T> accept(visitor: TokenVisitor<T>) = visitor.visit(this)
}
