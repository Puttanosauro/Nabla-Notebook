package org.example.project.qdcore.ast.quarkdown.invisible

import org.example.project.qdcore.ast.NestableNode
import org.example.project.qdcore.ast.Node
import org.example.project.qdcore.document.layout.page.PageMarginPosition
import org.example.project.qdcore.visitor.node.NodeVisitor

/**
 * A non-visible node that triggers a property in paged documents that allows displaying content on each page.
 * @param children content to be displayed on each page
 * @param position position of the content within the page
 */
class PageMarginContentInitializer(
    override val children: List<Node>,
    val position: PageMarginPosition,
) : NestableNode {
    override fun <T> accept(visitor: NodeVisitor<T>) = visitor.visit(this)
}
