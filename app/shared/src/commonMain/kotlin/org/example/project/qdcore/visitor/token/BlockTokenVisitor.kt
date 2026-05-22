package org.example.project.qdcore.visitor.token

import org.example.project.qdcore.lexer.tokens.BlockCodeToken
import org.example.project.qdcore.lexer.tokens.BlockQuoteToken
import org.example.project.qdcore.lexer.tokens.BlockTextToken
import org.example.project.qdcore.lexer.tokens.FencesCodeToken
import org.example.project.qdcore.lexer.tokens.FootnoteDefinitionToken
import org.example.project.qdcore.lexer.tokens.FunctionCallToken
import org.example.project.qdcore.lexer.tokens.HeadingToken
import org.example.project.qdcore.lexer.tokens.HorizontalRuleToken
import org.example.project.qdcore.lexer.tokens.HtmlToken
import org.example.project.qdcore.lexer.tokens.LinkDefinitionToken
import org.example.project.qdcore.lexer.tokens.ListItemToken
import org.example.project.qdcore.lexer.tokens.MultilineMathToken
import org.example.project.qdcore.lexer.tokens.NewlineToken
import org.example.project.qdcore.lexer.tokens.OnelineMathToken
import org.example.project.qdcore.lexer.tokens.OrderedListToken
import org.example.project.qdcore.lexer.tokens.PageBreakToken
import org.example.project.qdcore.lexer.tokens.ParagraphToken
import org.example.project.qdcore.lexer.tokens.SetextHeadingToken
import org.example.project.qdcore.lexer.tokens.TableToken
import org.example.project.qdcore.lexer.tokens.UnorderedListToken

/**
 * A visitor for block [org.example.project.qdcore.lexer.Token]s.
 * @param T output type of the `visit` methods
 */
interface BlockTokenVisitor<T> {
    fun visit(token: NewlineToken): T

    fun visit(token: BlockCodeToken): T

    fun visit(token: FencesCodeToken): T

    fun visit(token: HorizontalRuleToken): T

    fun visit(token: HeadingToken): T

    fun visit(token: SetextHeadingToken): T

    fun visit(token: LinkDefinitionToken): T

    fun visit(token: FootnoteDefinitionToken): T

    fun visit(token: UnorderedListToken): T

    fun visit(token: OrderedListToken): T

    fun visit(token: ListItemToken): T

    fun visit(token: TableToken): T

    fun visit(token: HtmlToken): T

    fun visit(token: ParagraphToken): T

    fun visit(token: BlockQuoteToken): T

    fun visit(token: BlockTextToken): T

    // Quarkdown extensions

    fun visit(token: PageBreakToken): T

    fun visit(token: MultilineMathToken): T

    fun visit(token: OnelineMathToken): T

    fun visit(token: FunctionCallToken): T
}
