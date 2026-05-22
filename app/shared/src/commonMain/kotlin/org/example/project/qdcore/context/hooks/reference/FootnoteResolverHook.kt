package org.example.project.qdcore.context.hooks.reference

import org.example.project.qdcore.ast.attributes.reference.ReferenceNode
import org.example.project.qdcore.ast.base.block.FootnoteDefinition
import org.example.project.qdcore.ast.base.block.setIndex
import org.example.project.qdcore.ast.base.inline.ReferenceFootnote
import org.example.project.qdcore.ast.iterator.ObservableAstIterator
import org.example.project.qdcore.context.MutableContext

/**
 * Hook that associates a [FootnoteDefinition] to each [ReferenceFootnote].
 */
class FootnoteResolverHook(
    context: MutableContext,
) : ReferenceDefinitionResolverHook<ReferenceFootnote, FootnoteDefinition, FootnoteDefinition>(context) {
    override fun collectReferences(iterator: ObservableAstIterator) = iterator.collectAll<ReferenceFootnote>()

    override fun collectDefinitions(iterator: ObservableAstIterator) = iterator.collectAll<FootnoteDefinition>()

    /**
     * Zips the references with their index in the list of references, grouped by their label.
     *
     * Example:
     * - `firstlabel`
     * - `secondlabel`
     * - `secondlabel`
     * - `firstlabel`
     * - `thirdlabel`
     *
     * Will assign the following indices to each:
     * - 0
     * - 1
     * - 1
     * - 0
     * - 2
     */
    override fun indexReferences(references: List<ReferenceNode<ReferenceFootnote, FootnoteDefinition>>) =
        references.groupBy { it.reference.label }.let { grouped ->
            // Assigns a stable index to each unique label.
            val labelToIndex = grouped.keys.withIndex().associate { (index, label) -> label to index }

            grouped.flatMap { (label, refs) ->
                val index = labelToIndex.getValue(label)
                refs.map { IndexedValue(index, it) }
            }
        }

    override fun findDefinitionPair(
        reference: ReferenceFootnote,
        definitions: List<FootnoteDefinition>,
        index: Int,
    ): Pair<FootnoteDefinition, FootnoteDefinition>? =
        definitions
            .find { it.label == reference.label }
            .also { it?.setIndex(context, index) }
            ?.let { it to it }
}
