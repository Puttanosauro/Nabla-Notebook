package org.example.project.qdcore.ast.quarkdown.block

import org.example.project.qdcore.ast.NestableNode
import org.example.project.qdcore.ast.Node
import org.example.project.qdcore.visitor.node.NodeVisitor

/**
 * Transposes content to landscape orientation by rotating it 90 degrees counter-clockwise
 * with respect to the page size.
 */
class Landscape(
    override val children: List<Node>,
) : NestableNode {
    override fun <T> accept(visitor: NodeVisitor<T>): T = visitor.visit(this)
}
