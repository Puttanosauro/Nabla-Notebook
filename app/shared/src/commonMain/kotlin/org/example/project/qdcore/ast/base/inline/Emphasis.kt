package org.example.project.qdcore.ast.base.inline

import org.example.project.qdcore.ast.InlineContent
import org.example.project.qdcore.ast.base.TextNode
import org.example.project.qdcore.visitor.node.NodeVisitor

/**
 * Weakly emphasized content.
 * @param text content
 */
class Emphasis(
    override val text: InlineContent,
) : TextNode {
    override fun <T> accept(visitor: NodeVisitor<T>) = visitor.visit(this)
}

/**
 * Strongly emphasized content.
 * @param text content
 */
class Strong(
    override val text: InlineContent,
) : TextNode {
    override fun <T> accept(visitor: NodeVisitor<T>) = visitor.visit(this)
}

/**
 * Heavily emphasized content.
 * @param text content
 */
class StrongEmphasis(
    override val text: InlineContent,
) : TextNode {
    override fun <T> accept(visitor: NodeVisitor<T>) = visitor.visit(this)
}

/**
 * Strikethrough content.
 * @param text content
 */
class Strikethrough(
    override val text: InlineContent,
) : TextNode {
    override fun <T> accept(visitor: NodeVisitor<T>) = visitor.visit(this)
}
