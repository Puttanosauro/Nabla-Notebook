package org.example.project.qdcore.ast.quarkdown.bibliography

import org.example.project.qdcore.ast.attributes.reference.ReferenceNode
import org.example.project.qdcore.bibliography.BibliographyEntry
import org.example.project.qdcore.visitor.node.NodeVisitor

/**
 * Represents a citation to one or more bibliography entries.
 * Multiple keys produce a single combined label (e.g. `[1, 3]` or `(Einstein, 1905; Hawking, 1988)`),
 * according to the active citation style.
 * @param citationKeys the keys used to identify the bibliography entries
 */
class BibliographyCitation(
    val citationKeys: List<String>,
) : ReferenceNode<BibliographyCitation, Pair<List<BibliographyEntry>, BibliographyView>> {
    override val reference: BibliographyCitation = this

    override fun <T> accept(visitor: NodeVisitor<T>): T = visitor.visit(this)
}
