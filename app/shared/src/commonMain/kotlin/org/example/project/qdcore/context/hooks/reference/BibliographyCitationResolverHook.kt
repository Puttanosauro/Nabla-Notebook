package org.example.project.qdcore.context.hooks.reference

import org.example.project.qdcore.ast.attributes.reference.ReferenceNode
import org.example.project.qdcore.ast.attributes.reference.setCitationLabel
import org.example.project.qdcore.ast.iterator.ObservableAstIterator
import org.example.project.qdcore.ast.quarkdown.bibliography.BibliographyCitation
import org.example.project.qdcore.ast.quarkdown.bibliography.BibliographyView
import org.example.project.qdcore.bibliography.Bibliography
import org.example.project.qdcore.bibliography.BibliographyEntry
import org.example.project.qdcore.context.MutableContext

/**
 * Hook that associates bibliography entries to each [BibliographyCitation]
 * that can be linked to entries of a [Bibliography]
 * within a [BibliographyView].
 *
 * After each citation is resolved, this hook pre-computes its citation label in document order
 * and stores it as a [org.example.project.qdcore.ast.attributes.reference.CitationLabelProperty] on the node,
 * so that parallel rendering can safely read it without depending on visit order.
 */
class BibliographyCitationResolverHook(
    context: MutableContext,
) : ReferenceDefinitionResolverHook<BibliographyCitation, BibliographyView, Pair<List<BibliographyEntry>, BibliographyView>>(context) {
    override fun collectReferences(iterator: ObservableAstIterator) = iterator.collectAll<BibliographyCitation>()

    override fun collectDefinitions(iterator: ObservableAstIterator) = iterator.collectAll<BibliographyView>()

    override fun findDefinitionPair(
        reference: BibliographyCitation,
        definitions: List<BibliographyView>,
        index: Int,
    ): Pair<BibliographyView, Pair<List<BibliographyEntry>, BibliographyView>>? =
        definitions
            .firstNotNullOfOrNull { bibliography ->
                val entries =
                    reference.citationKeys.map { key ->
                        bibliography.bibliography.entries[key] ?: return@firstNotNullOfOrNull null
                    }
                bibliography to (entries to bibliography)
            }

    override fun onResolved(
        reference: ReferenceNode<BibliographyCitation, Pair<List<BibliographyEntry>, BibliographyView>>,
        definition: Pair<List<BibliographyEntry>, BibliographyView>,
    ) {
        val (entries, view) = definition
        val label = view.style.labelProvider.getCitationLabel(entries)
        reference.reference.setCitationLabel(context, label)
    }
}
