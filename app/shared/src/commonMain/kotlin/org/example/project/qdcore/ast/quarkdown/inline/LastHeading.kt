package org.example.project.qdcore.ast.quarkdown.inline

import org.example.project.qdcore.ast.Node
import org.example.project.qdcore.visitor.node.NodeVisitor

/**
 * Node that displays the last heading encountered, of the given [depth], before the current position.
 * @param depth the depth of the last [org.example.project.qdcore.ast.base.block.Heading] to match
 */
class LastHeading(
    val depth: Int,
) : Node {
    override fun <T> accept(visitor: NodeVisitor<T>): T = visitor.visit(this)
}
