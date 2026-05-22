package org.example.project.qdcore.ast.base.inline

import org.example.project.qdcore.ast.Node
import org.example.project.qdcore.visitor.node.NodeVisitor

/**
 * A hard line break.
 */
object LineBreak : Node {
    override fun <T> accept(visitor: NodeVisitor<T>) = visitor.visit(this)
}
