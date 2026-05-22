package org.example.project.qdcore.ast.base.block.list

import org.example.project.qdcore.ast.NestableNode
import org.example.project.qdcore.ast.Node
import org.example.project.qdcore.visitor.node.NodeVisitor

/**
 * An item of a [ListBlock]. A list item may be enhanced via [ListItemVariant]s.
 * @param variants additional functionalities and characteristics of this item. For example, this item may contain a checked/unchecked task.
 * @param children content
 */
class ListItem(
    val variants: List<ListItemVariant> = emptyList(),
    override val children: List<Node>,
) : NestableNode {
    /**
     * The list that owns this item.
     * This property is set by the parser and should not be externally modified.
     */
    var owner: ListBlock? = null

    override fun <T> accept(visitor: NodeVisitor<T>) = visitor.visit(this)
}
