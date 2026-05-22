package org.example.project.qdcore.ast.base.block

import org.example.project.qdcore.ast.InlineContent
import org.example.project.qdcore.ast.base.TextNode
import org.example.project.qdcore.visitor.node.NodeVisitor

/**
 * A general paragraph.
 * @param text text content
 */
class Paragraph(
    override val text: InlineContent,
) : TextNode {
    override fun <T> accept(visitor: NodeVisitor<T>) = visitor.visit(this)
}
