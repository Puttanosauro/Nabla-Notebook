package org.example.project.qdcore.ast.quarkdown.block

import org.example.project.qdcore.ast.NestableNode
import org.example.project.qdcore.ast.Node
import org.example.project.qdcore.rendering.representable.RenderRepresentable
import org.example.project.qdcore.rendering.representable.RenderRepresentableVisitor
import org.example.project.qdcore.visitor.node.NodeVisitor

/**
 * A block whose content is clipped in a path.
 * @param clip type of the clip path
 */
class Clipped(
    val clip: Clip,
    override val children: List<Node>,
) : NestableNode {
    override fun <T> accept(visitor: NodeVisitor<T>) = visitor.visit(this)

    /**
     * Possible clip types of a [Clipped] block.
     */
    enum class Clip : RenderRepresentable {
        CIRCLE,
        ;

        override fun <T> accept(visitor: RenderRepresentableVisitor<T>) = visitor.visit(this)
    }
}
