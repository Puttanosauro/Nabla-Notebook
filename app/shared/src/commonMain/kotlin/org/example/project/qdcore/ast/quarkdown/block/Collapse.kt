package org.example.project.qdcore.ast.quarkdown.block

import org.example.project.qdcore.ast.InlineContent
import org.example.project.qdcore.ast.NestableNode
import org.example.project.qdcore.ast.Node
import org.example.project.qdcore.util.node.group
import org.example.project.qdcore.visitor.node.NodeVisitor

/**
 * A collapsible block, whose content can be hidden or shown by interacting with it.
 * @param title title of the block
 * @param isOpen whether the block is open at the beginning
 * @param content body content of the block
 */
class Collapse(
    val title: InlineContent,
    val isOpen: Boolean,
    val content: List<Node>,
) : NestableNode {
    override val children: List<Node>
        get() = content + title.group()

    override fun <T> accept(visitor: NodeVisitor<T>): T = visitor.visit(this)
}
