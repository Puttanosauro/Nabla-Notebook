package org.example.project.qdcore.ast

import org.example.project.qdcore.visitor.node.NodeVisitor

/**
 * The root of a node tree.
 */
class AstRoot(
    override val children: List<Node>,
) : NestableNode {
    override fun <T> accept(visitor: NodeVisitor<T>) = visitor.visit(this)
}

typealias Document = AstRoot
