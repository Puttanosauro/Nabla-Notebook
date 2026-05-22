package org.example.project.qdcore.ast.base.block

import org.example.project.qdcore.ast.Node
import org.example.project.qdcore.visitor.node.NodeVisitor

/**
 * A blank line.
 */
object Newline : Node {
    override fun <T> accept(visitor: NodeVisitor<T>) = visitor.visit(this)
}
