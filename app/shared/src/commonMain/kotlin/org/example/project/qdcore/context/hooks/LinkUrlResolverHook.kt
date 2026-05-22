package org.example.project.qdcore.context.hooks

import org.example.project.qdcore.ast.attributes.link.setResolvedUrl
import org.example.project.qdcore.ast.base.LinkNode
import org.example.project.qdcore.ast.base.block.LinkDefinition
import org.example.project.qdcore.ast.base.inline.Image
import org.example.project.qdcore.ast.iterator.AstIteratorHook
import org.example.project.qdcore.ast.iterator.ObservableAstIterator
import org.example.project.qdcore.context.MutableContext
import org.example.project.qdcore.media.passthrough.MediaPassthrough
import org.example.project.qdcore.util.isURL
import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.invariantSeparatorsPathString

/**
 * Hook that resolves relative link paths based on their file system.
 *
 * If a link uses a relative path and its file system
 * is different from the [context]'s file system,
 * the path is resolved relative to the context's file system.
 *
 * This is mainly applied to images.
 *
 * @param context root context to use for resolution
 * @see org.example.project.qdcore.ast.attributes.link.ResolvedLinkUrlProperty
 */
class LinkUrlResolverHook(
    private val context: MutableContext,
) : AstIteratorHook {
    /**
     * Resolves the URL of a [link] if it's a relative path
     * and its file system is different from the [context]'s file system.
     *
     * @param link link node to resolve
     */
    private fun resolve(link: LinkNode) {
        val fileSystem = link.fileSystem

        if (fileSystem == null || fileSystem.isRoot) return // No need to resolve paths.
        if (MediaPassthrough.isPassthroughPath(link.url)) return // No need to resolve passthrough paths.
        if (link.url.isURL || Path(link.url).isAbsolute) return // Not a relative path.

        val resolved: Path? =
            context.fileSystem
                .relativePathTo(fileSystem)
                ?.resolve(link.url)
                ?.normalize()

        resolved?.let {
            link.setResolvedUrl(context, it.invariantSeparatorsPathString)
        }
    }

    override fun attach(iterator: ObservableAstIterator) {
        iterator.on<Image> { resolve(it.link) }
        iterator.on<LinkDefinition> { resolve(it) }
    }
}
