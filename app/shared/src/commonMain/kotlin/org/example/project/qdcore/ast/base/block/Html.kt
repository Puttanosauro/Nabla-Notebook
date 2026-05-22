package org.example.project.qdcore.ast.base.block

import org.example.project.qdcore.ast.Node
import org.example.project.qdcore.visitor.node.NodeVisitor

/**
 * An HTML block.
 * @param content raw HTML content
 */
class Html(
    val content: String,
) : Node {
    override fun <T> accept(visitor: NodeVisitor<T>) = visitor.visit(this)
}
