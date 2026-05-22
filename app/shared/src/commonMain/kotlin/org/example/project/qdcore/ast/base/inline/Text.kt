package org.example.project.qdcore.ast.base.inline

import org.example.project.qdcore.ast.Node
import org.example.project.qdcore.visitor.node.NodeVisitor

/**
 * A [Node] that contains plain text.
 * @see org.example.project.qdcore.util.node.toPlainText
 */
interface PlainTextNode : Node {
    val text: String
}

/**
 * Content (usually a single character) that requires special treatment during the rendering stage.
 * @param text wrapped text
 */
class CriticalContent(
    override val text: String,
) : PlainTextNode {
    override fun <T> accept(visitor: NodeVisitor<T>) = visitor.visit(this)
}

/**
 * Plain inline text.
 * @param text text content.
 */
class Text(
    override val text: String,
) : PlainTextNode {
    override fun <T> accept(visitor: NodeVisitor<T>) = visitor.visit(this)
}
