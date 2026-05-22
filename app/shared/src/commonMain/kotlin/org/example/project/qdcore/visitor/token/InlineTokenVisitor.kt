package org.example.project.qdcore.visitor.token

import org.example.project.qdcore.lexer.tokens.CodeSpanToken
import org.example.project.qdcore.lexer.tokens.CommentToken
import org.example.project.qdcore.lexer.tokens.CriticalContentToken
import org.example.project.qdcore.lexer.tokens.DiamondAutolinkToken
import org.example.project.qdcore.lexer.tokens.EmphasisToken
import org.example.project.qdcore.lexer.tokens.EntityToken
import org.example.project.qdcore.lexer.tokens.EscapeToken
import org.example.project.qdcore.lexer.tokens.ImageToken
import org.example.project.qdcore.lexer.tokens.InlineMathToken
import org.example.project.qdcore.lexer.tokens.LineBreakToken
import org.example.project.qdcore.lexer.tokens.LinkToken
import org.example.project.qdcore.lexer.tokens.PlainTextToken
import org.example.project.qdcore.lexer.tokens.ReferenceFootnoteToken
import org.example.project.qdcore.lexer.tokens.ReferenceImageToken
import org.example.project.qdcore.lexer.tokens.ReferenceLinkToken
import org.example.project.qdcore.lexer.tokens.StrikethroughToken
import org.example.project.qdcore.lexer.tokens.StrongEmphasisToken
import org.example.project.qdcore.lexer.tokens.StrongToken
import org.example.project.qdcore.lexer.tokens.TextSymbolToken
import org.example.project.qdcore.lexer.tokens.UrlAutolinkToken

/**
 * A visitor for inline [org.example.project.qdcore.lexer.Token]s.
 * @param T output type of the `visit` methods
 */
interface InlineTokenVisitor<T> {
    fun visit(token: EscapeToken): T

    fun visit(token: EntityToken): T

    fun visit(token: CriticalContentToken): T

    fun visit(token: TextSymbolToken): T

    fun visit(token: CommentToken): T

    fun visit(token: LineBreakToken): T

    fun visit(token: LinkToken): T

    fun visit(token: ReferenceLinkToken): T

    fun visit(token: ReferenceFootnoteToken): T

    fun visit(token: DiamondAutolinkToken): T

    fun visit(token: UrlAutolinkToken): T

    fun visit(token: ImageToken): T

    fun visit(token: ReferenceImageToken): T

    fun visit(token: CodeSpanToken): T

    // Emphasis

    fun visit(token: PlainTextToken): T

    fun visit(token: EmphasisToken): T

    fun visit(token: StrongToken): T

    fun visit(token: StrongEmphasisToken): T

    fun visit(token: StrikethroughToken): T

    // Quarkdown extensions

    fun visit(token: InlineMathToken): T
}
