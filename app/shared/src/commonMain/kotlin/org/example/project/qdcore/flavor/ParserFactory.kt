package org.example.project.qdcore.flavor

import org.example.project.qdcore.ast.Node
import org.example.project.qdcore.context.MutableContext
import org.example.project.qdcore.visitor.token.BlockTokenVisitor
import org.example.project.qdcore.visitor.token.InlineTokenVisitor
import org.example.project.qdcore.visitor.token.TokenVisitor
import org.example.project.qdcore.visitor.token.TokenVisitorAdapter

/**
 * Provider of parser instances. Each factory method returns a specialized implementation for a specific kind of parsing.
 */
interface ParserFactory {
    /**
     * @param context writeable context data that is modified during the parsing process,
     *                which carries useful information for the next stages of the pipeline
     * @return a new [BlockTokenVisitor] instance that parses tokens into [Node]s.
     */
    fun newBlockParser(context: MutableContext): BlockTokenVisitor<Node>

    /**
     * @param context writeable context data that is modified during the parsing process,
     *                which carries useful information for the next stages of the pipeline
     * @return a new [BlockTokenVisitor] instance that parses tokens into [Node]s.
     */
    fun newInlineParser(context: MutableContext): InlineTokenVisitor<Node>

    /**
     * @param context writeable context data that is modified during the parsing process,
     *                which carries useful information for the next stages of the pipeline
     * @return a new [TokenVisitor] instance that includes operations by both [newBlockParser] and [newInlineParser]
     */
    fun newParser(context: MutableContext): TokenVisitor<Node> = TokenVisitorAdapter(newBlockParser(context), newInlineParser(context))
}
