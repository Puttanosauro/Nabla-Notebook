package org.example.project.qdcore.ast.quarkdown.inline

import org.example.project.qdcore.ast.InlineContent
import org.example.project.qdcore.ast.base.TextNode
import org.example.project.qdcore.visitor.node.NodeVisitor

/**
 * A collapsible block, whose content can be hidden or shown by interacting with it.
 * @param text expanded content
 * @param placeholder content to show when the node is collapsed
 * @param isOpen whether the node is expanded at the beginning
 */
class InlineCollapse(
    override val text: InlineContent,
    val placeholder: InlineContent,
    val isOpen: Boolean,
) : TextNode {
    override fun <T> accept(visitor: NodeVisitor<T>): T = visitor.visit(this)

    companion object {
        /**
         * A default placeholder for the collapsed state of a [InlineCollapse].
         */
        const val DEFAULT_PLACEHOLDER = "(...)"
    }
}
