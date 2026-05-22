package org.example.project.qdcore.ast.quarkdown.block

import org.example.project.qdcore.ast.Node
import org.example.project.qdcore.visitor.node.NodeVisitor

/**
 * A Mermaid diagram.
 * @param code Mermaid code of the diagram
 */
class MermaidDiagram(
    val code: String,
) : Node {
    override fun <T> accept(visitor: NodeVisitor<T>) = visitor.visit(this)
}
