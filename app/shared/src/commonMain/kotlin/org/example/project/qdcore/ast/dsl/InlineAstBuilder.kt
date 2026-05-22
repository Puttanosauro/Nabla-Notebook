package org.example.project.qdcore.ast.dsl

import org.example.project.qdcore.ast.InlineContent
import org.example.project.qdcore.ast.base.inline.CodeSpan
import org.example.project.qdcore.ast.base.inline.Emphasis
import org.example.project.qdcore.ast.base.inline.Image
import org.example.project.qdcore.ast.base.inline.LineBreak
import org.example.project.qdcore.ast.base.inline.Link
import org.example.project.qdcore.ast.base.inline.Strong
import org.example.project.qdcore.ast.base.inline.StrongEmphasis
import org.example.project.qdcore.ast.base.inline.Text
import org.example.project.qdcore.ast.quarkdown.inline.InlineCollapse
import org.example.project.qdcore.ast.quarkdown.inline.TextTransform
import org.example.project.qdcore.ast.quarkdown.inline.TextTransformData
import org.example.project.qdcore.context.file.SimpleFileSystem
import org.example.project.qdcore.document.size.Size

/**
 * A builder of inline nodes.
 */
class InlineAstBuilder : AstBuilder() {
    /**
     * @see Strong
     */
    fun strong(block: InlineAstBuilder.() -> Unit) = +Strong(buildInline(block))

    /**
     * @see Emphasis
     */
    fun emphasis(block: InlineAstBuilder.() -> Unit) = +Emphasis(buildInline(block))

    /**
     * @see StrongEmphasis
     */
    fun strongEmphasis(block: InlineAstBuilder.() -> Unit) = +StrongEmphasis(buildInline(block))

    /**
     * @see Text
     */
    fun text(text: String) = +Text(text)

    /**
     * @see TextTransform
     */
    fun text(
        text: String,
        transform: TextTransformData,
    ) = +TextTransform(transform, children = buildInline { text(text) })

    /**
     * @see Link
     */
    fun link(
        url: String,
        title: String? = null,
        label: InlineAstBuilder.() -> Unit,
    ) = +Link(buildInline(label), url, title?.let { listOf(Text(it)) })

    /**
     * @see CodeSpan
     */
    fun codeSpan(text: String) = +CodeSpan(text)

    /**
     * @see Image
     */
    fun image(
        url: String,
        title: String? = null,
        width: Size? = null,
        height: Size? = null,
        referenceId: String? = null,
        label: InlineAstBuilder.() -> Unit = {},
    ) = +Image(
        Link(buildInline(label), url, title?.let { listOf(Text(it)) }, fileSystem = SimpleFileSystem()),
        width,
        height,
        referenceId,
    )

    /**
     * @see InlineCollapse
     */
    fun collapse(
        text: InlineAstBuilder.() -> Unit,
        placeholder: InlineAstBuilder.() -> Unit = { text(InlineCollapse.DEFAULT_PLACEHOLDER) },
        isOpen: Boolean = false,
    ) = +InlineCollapse(buildInline(text), buildInline(placeholder), isOpen)

    /**
     * Automatically collapses a text if its length exceeds [maxLength].
     * @see InlineCollapse
     */
    fun autoCollapse(
        text: String,
        maxLength: Int,
    ) = collapse(
        text = { text(text) },
        isOpen = text.length <= maxLength,
    )

    /**
     * @see LineBreak
     */
    fun lineBreak() = +LineBreak
}

/**
 * Begins a DSL block for building inline content.
 * @param block action to run with the inline builder
 * @return the built nodes
 * @see InlineAstBuilder
 */
fun buildInline(block: InlineAstBuilder.() -> Unit): InlineContent = InlineAstBuilder().apply(block).build()
