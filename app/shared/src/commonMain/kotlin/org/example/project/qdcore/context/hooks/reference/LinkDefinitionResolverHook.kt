package org.example.project.qdcore.context.hooks.reference

import org.example.project.qdcore.ast.attributes.link.getResolvedUrl
import org.example.project.qdcore.ast.attributes.reference.ReferenceNode
import org.example.project.qdcore.ast.base.LinkNode
import org.example.project.qdcore.ast.base.block.LinkDefinition
import org.example.project.qdcore.ast.base.inline.Link
import org.example.project.qdcore.ast.base.inline.ReferenceImage
import org.example.project.qdcore.ast.base.inline.ReferenceLink
import org.example.project.qdcore.ast.iterator.ObservableAstIterator
import org.example.project.qdcore.context.MutableContext
import org.example.project.qdcore.util.node.toPlainText

/**
 * Hook that associates a [LinkDefinition] to each [ReferenceLink],
 * producing a resolved [Link] node that can be retrieved via
 * [org.example.project.qdcore.ast.attributes.reference.getDefinition].
 *
 * When a match is found, [ReferenceLink.onResolve] callbacks are triggered,
 * which are used by [org.example.project.qdcore.context.hooks.MediaStorerHook]
 * to register media for reference images.
 */
class LinkDefinitionResolverHook(
    context: MutableContext,
) : ReferenceDefinitionResolverHook<ReferenceLink, LinkDefinition, LinkNode>(context) {
    override fun collectReferences(iterator: ObservableAstIterator): List<ReferenceNode<ReferenceLink, LinkNode>> {
        val references = mutableListOf<ReferenceNode<ReferenceLink, LinkNode>>()
        iterator.on<ReferenceLink> { references += it }
        iterator.on<ReferenceImage> { references += it.link }
        return references
    }

    override fun collectDefinitions(iterator: ObservableAstIterator) = iterator.collectAll<LinkDefinition>()

    override fun findDefinitionPair(
        reference: ReferenceLink,
        definitions: List<LinkDefinition>,
        index: Int,
    ): Pair<LinkDefinition, LinkNode>? =
        definitions
            .find { it.label.toPlainText() == reference.referenceLabel.toPlainText() }
            ?.let { definition ->
                val link = Link(reference.label, definition.getResolvedUrl(context), definition.title, definition.fileSystem)
                reference.onResolve.forEach { action -> action(link) }
                definition to link
            }
}
