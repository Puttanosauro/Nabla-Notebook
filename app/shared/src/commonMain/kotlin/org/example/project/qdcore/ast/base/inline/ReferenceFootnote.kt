package org.example.project.qdcore.ast.base.inline

import org.example.project.qdcore.ast.AstRoot
import org.example.project.qdcore.ast.InlineContent
import org.example.project.qdcore.ast.NestableNode
import org.example.project.qdcore.ast.Node
import org.example.project.qdcore.ast.attributes.reference.ReferenceNode
import org.example.project.qdcore.ast.base.block.FootnoteDefinition
import org.example.project.qdcore.visitor.node.NodeVisitor

/**
 * A reference to a [org.example.project.qdcore.ast.base.block.FootnoteDefinition].
 * @param label reference label that should match that of the footnote definition
 * @param fallback supplier of the node to show instead of [label] in case the reference is invalid
 */
class ReferenceFootnote(
    val label: String,
    val fallback: () -> Node,
) : ReferenceNode<ReferenceFootnote, FootnoteDefinition> {
    override val reference: ReferenceFootnote = this

    override fun <T> accept(visitor: NodeVisitor<T>) = visitor.visit(this)
}

/**
 * An all-in-one [ReferenceFootnote] that includes its [FootnoteDefinition].
 * @param label the new label of the definition and reference
 * @param definition the content of the footnote definition
 */
class ReferenceDefinitionFootnote(
    val label: String,
    val definition: InlineContent,
) : NestableNode {
    override val children =
        listOf(
            ReferenceFootnote(
                label,
                fallback = { throw IllegalStateException("Reference + definition footnote should not need a fallback") },
            ),
            FootnoteDefinition(
                label,
                definition,
            ),
        )

    override fun <T> accept(visitor: NodeVisitor<T>): T = AstRoot(children).accept(visitor)
}
