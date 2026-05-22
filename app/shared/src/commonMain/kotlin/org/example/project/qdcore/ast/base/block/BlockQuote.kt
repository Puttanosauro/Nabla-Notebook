package org.example.project.qdcore.ast.base.block

import org.example.project.qdcore.ast.InlineContent
import org.example.project.qdcore.ast.NestableNode
import org.example.project.qdcore.ast.Node
import org.example.project.qdcore.rendering.representable.RenderRepresentable
import org.example.project.qdcore.rendering.representable.RenderRepresentableVisitor
import org.example.project.qdcore.util.node.group
import org.example.project.qdcore.visitor.node.NodeVisitor

/**
 * A block quote.
 * @param type information type. If `null`, the quote does not have a particular type
 * @param attribution additional author or source of the quote
 * @param content body content of the quote
 */
class BlockQuote(
    val type: Type? = null,
    val attribution: InlineContent? = null,
    val content: List<Node>,
) : NestableNode {
    override val children: List<Node>
        get() = content + attribution.group()

    override fun <T> accept(visitor: NodeVisitor<T>) = visitor.visit(this)

    /**
     * Type a [BlockQuote] might have.
     */
    enum class Type : RenderRepresentable {
        TIP,
        NOTE,
        WARNING,
        IMPORTANT,
        ;

        override fun <T> accept(visitor: RenderRepresentableVisitor<T>): T = visitor.visit(this)
    }
}
