package org.example.project.qdcore.ast.base

import org.example.project.qdcore.ast.InlineContent
import org.example.project.qdcore.ast.attributes.error.ErrorCapableNode
import org.example.project.qdcore.context.file.FileSystem

/**
 * A general link node.
 * A link can error for various reasons.
 * For example, a [org.example.project.qdcore.ast.base.inline.SubdocumentLink] can error
 * if the linked subdocument cannot be found.
 *
 * @see org.example.project.qdcore.ast.base.inline.Link
 * @see org.example.project.qdcore.ast.base.block.LinkDefinition
 */
interface LinkNode : ErrorCapableNode {
    /**
     * Inline content of the displayed label.
     */
    val label: InlineContent

    /**
     * URL this link points to.
     */
    val url: String

    /**
     * Optional title.
     */
    val title: InlineContent?

    /**
     * Optional file system where this link is defined, used for resolving relative paths.
     * @see org.example.project.qdcore.context.hooks.LinkUrlResolverHook
     */
    val fileSystem: FileSystem?

    /**
     * Creates a copy of this link with the given [url].
     */
    fun copy(url: String): LinkNode
}
