package org.example.project.qdcore.ast.base.block

import org.example.project.qdcore.ast.Node
import org.example.project.qdcore.visitor.node.NodeVisitor

/**
 * Any unknown node type (should not happen).
 */
object BlankNode : Node {
    override fun <T> accept(visitor: NodeVisitor<T>) = visitor.visit(this)
}
