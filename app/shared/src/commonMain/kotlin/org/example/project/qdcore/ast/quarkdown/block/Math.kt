package org.example.project.qdcore.ast.quarkdown.block

import org.example.project.qdcore.ast.attributes.location.LocationTrackableNode
import org.example.project.qdcore.ast.quarkdown.reference.CrossReferenceableNode
import org.example.project.qdcore.visitor.node.NodeVisitor

/**
 * A math (TeX) block.
 *
 * A math block can be cross-referenced and can be numbered, as long as it has a [referenceId].
 * @param expression expression content
 * @param referenceId optional reference id for cross-referencing via a [org.example.project.qdcore.ast.quarkdown.reference.CrossReference]
 */
class Math(
    val expression: String,
    override val referenceId: String? = null,
) : LocationTrackableNode,
    CrossReferenceableNode {
    /**
     * A math block is numbered if it has a [referenceId].
     */
    override val canTrackLocation: Boolean
        get() = referenceId != null

    override fun <T> accept(visitor: NodeVisitor<T>) = visitor.visit(this)
}
