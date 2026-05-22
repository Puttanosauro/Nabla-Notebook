package org.example.project.qdcore.ast.base.block

import org.example.project.qdcore.ast.InlineContent
import org.example.project.qdcore.ast.attributes.localization.LocalizedKind
import org.example.project.qdcore.ast.attributes.localization.LocalizedKindKeys
import org.example.project.qdcore.ast.attributes.location.LocationTrackableNode
import org.example.project.qdcore.ast.quarkdown.CaptionableNode
import org.example.project.qdcore.ast.quarkdown.reference.CrossReferenceableNode
import org.example.project.qdcore.function.value.data.Range
import org.example.project.qdcore.visitor.node.NodeVisitor

/**
 * A code block.
 * @param content code content
 * @param language optional syntax language
 * @param showLineNumbers whether to show line numbers
 * @param highlight whether to apply syntax highlighting
 * @param focusedLines range of lines to focus on. No lines are focused if `null`
 * @param caption optional caption
 * @param referenceId optional ID for cross-referencing via a [org.example.project.qdcore.ast.quarkdown.reference.CrossReference]
 */
class Code(
    val content: String,
    val language: String?,
    val showLineNumbers: Boolean = true,
    val highlight: Boolean = true,
    val focusedLines: Range? = null,
    override val caption: InlineContent? = null,
    override val referenceId: String? = null,
) : LocationTrackableNode,
    CrossReferenceableNode,
    CaptionableNode,
    LocalizedKind {
    override val kindLocalizationKey: String
        get() = LocalizedKindKeys.CODE_BLOCK

    override fun <T> accept(visitor: NodeVisitor<T>) = visitor.visit(this)
}
