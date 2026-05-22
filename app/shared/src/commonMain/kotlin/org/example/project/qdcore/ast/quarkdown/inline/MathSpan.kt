package org.example.project.qdcore.ast.quarkdown.inline

import org.example.project.qdcore.ast.Node
import org.example.project.qdcore.visitor.node.NodeVisitor

/**
 * A math (TeX) inline.
 * @param expression expression content
 */
class MathSpan(
    val expression: String,
) : Node {
    override fun <T> accept(visitor: NodeVisitor<T>) = visitor.visit(this)
}
