package org.example.project.qdcore.ast.base

import org.example.project.qdcore.ast.InlineContent
import org.example.project.qdcore.ast.NestableNode
import org.example.project.qdcore.ast.Node

/**
 * A node that may contain inline content as its children.
 */
interface TextNode : NestableNode {
    /**
     * The text of the node as processed inline content.
     */
    val text: InlineContent

    override val children: List<Node>
        get() = text
}
