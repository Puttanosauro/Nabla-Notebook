package org.example.project.qdcore.ast.quarkdown.reference

import org.example.project.qdcore.ast.attributes.reference.ReferenceNode
import org.example.project.qdcore.visitor.node.NodeVisitor

/**
 * A cross-reference to a [CrossReferenceableNode] within the same document.
 * The link with the target is made by matching [referenceId]s.
 * @param referenceId the reference ID of the target node being referenced
 */
class CrossReference(
    val referenceId: String,
) : ReferenceNode<CrossReference, CrossReferenceableNode> {
    override val reference: CrossReference = this

    override fun <T> accept(visitor: NodeVisitor<T>): T = visitor.visit(this)
}
