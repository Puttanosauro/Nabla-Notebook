package org.example.project.qdcore.context.hooks.reference

import org.example.project.qdcore.ast.iterator.ObservableAstIterator
import org.example.project.qdcore.ast.quarkdown.reference.CrossReference
import org.example.project.qdcore.ast.quarkdown.reference.CrossReferenceableNode
import org.example.project.qdcore.context.MutableContext

/**
 * A [ReferenceDefinitionResolverHook] that associates a [CrossReferenceableNode] to each [CrossReference] by means of matching IDs.
 */
class CrossReferenceResolverHook(
    context: MutableContext,
) : ReferenceDefinitionResolverHook<CrossReference, CrossReferenceableNode, CrossReferenceableNode>(context) {
    override fun collectReferences(iterator: ObservableAstIterator) = iterator.collectAll<CrossReference>()

    override fun collectDefinitions(iterator: ObservableAstIterator) = iterator.collectAll<CrossReferenceableNode>()

    override fun findDefinitionPair(
        reference: CrossReference,
        definitions: List<CrossReferenceableNode>,
        index: Int,
    ): Pair<CrossReferenceableNode, CrossReferenceableNode>? =
        definitions
            .find { reference.referenceId == it.referenceId }
            ?.let { it to it }
}
