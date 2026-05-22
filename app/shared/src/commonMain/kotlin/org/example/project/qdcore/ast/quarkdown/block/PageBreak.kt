package org.example.project.qdcore.ast.quarkdown.block

import org.example.project.qdcore.ast.Node
import org.example.project.qdcore.visitor.node.NodeVisitor

/**
 * A forced page break.
 */
class PageBreak : Node {
    override fun toString() = "PageBreak"

    override fun <T> accept(visitor: NodeVisitor<T>) = visitor.visit(this)
}
