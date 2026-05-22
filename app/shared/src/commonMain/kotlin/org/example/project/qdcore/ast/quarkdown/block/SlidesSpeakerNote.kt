package org.example.project.qdcore.ast.quarkdown.block

import org.example.project.qdcore.ast.NestableNode
import org.example.project.qdcore.ast.Node
import org.example.project.qdcore.visitor.node.NodeVisitor

/**
 * A node that, when rendered in a `Slides` environment in speaker view,
 * contains speaker notes for the current slide.
 */
class SlidesSpeakerNote(
    override val children: List<Node>,
) : NestableNode {
    override fun <T> accept(visitor: NodeVisitor<T>): T = visitor.visit(this)
}
