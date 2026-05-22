package org.example.project.qdcore.ast.quarkdown.bibliography

import org.example.project.qdcore.ast.Node
import org.example.project.qdcore.bibliography.Bibliography
import org.example.project.qdcore.bibliography.style.BibliographyStyle
import org.example.project.qdcore.visitor.node.NodeVisitor

/**
 * Renderable container of a [bibliography].
 * @param bibliography the bibliography to render
 * @param style the style to use for rendering the bibliography
 */
class BibliographyView(
    val bibliography: Bibliography,
    val style: BibliographyStyle,
) : Node {
    override fun <T> accept(visitor: NodeVisitor<T>): T = visitor.visit(this)
}
