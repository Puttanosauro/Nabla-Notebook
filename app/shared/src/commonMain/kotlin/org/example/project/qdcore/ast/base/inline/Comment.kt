package org.example.project.qdcore.ast.base.inline

import org.example.project.qdcore.ast.Node
import org.example.project.qdcore.visitor.node.NodeVisitor

/**
 * A comment whose content is ignored.
 */
object Comment : Node {
    override fun <T> accept(visitor: NodeVisitor<T>) = visitor.visit(this)
}
