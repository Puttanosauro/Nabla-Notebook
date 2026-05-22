package org.example.project.qdcore.ast.quarkdown.invisible

import org.example.project.qdcore.ast.Node
import org.example.project.qdcore.visitor.node.NodeVisitor

/**
 * Marker node used to specify the format of page numbers during rendering.
 * @param format the format string for page numbers, e.g. "1", "i", "A".
 */
class PageNumberFormatter(
    val format: String,
) : Node {
    override fun <T> accept(visitor: NodeVisitor<T>): T = visitor.visit(this)
}
