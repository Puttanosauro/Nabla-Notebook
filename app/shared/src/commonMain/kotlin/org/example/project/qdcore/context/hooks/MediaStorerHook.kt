package org.example.project.qdcore.context.hooks

import org.example.project.qdcore.ast.attributes.error.setError
import org.example.project.qdcore.ast.attributes.link.getResolvedUrl
import org.example.project.qdcore.ast.base.LinkNode
import org.example.project.qdcore.ast.base.inline.Image
import org.example.project.qdcore.ast.base.inline.ReferenceImage
import org.example.project.qdcore.ast.iterator.AstIteratorHook
import org.example.project.qdcore.ast.iterator.ObservableAstIterator
import org.example.project.qdcore.ast.media.StoredMediaProperty
import org.example.project.qdcore.context.MutableContext
import org.example.project.qdcore.log.Log
import org.example.project.qdcore.media.passthrough.MediaPassthrough
import org.example.project.qdcore.media.storage.StoredMedia
import org.example.project.qdcore.permissions.MissingPermissionException

/**
 * Hook that, when a node containing information about media is found,
 * registers it in the media storage of [MutableContext.mediaStorage].
 * A media storage is a temporary lookup table that maps media to their paths, so that they can be resolved later.
 * @param context the context containing the media storage to register media into
 */
class MediaStorerHook(
    private val context: MutableContext,
) : AstIteratorHook {
    /**
     * Registers a media contained within a link into the media storage
     * and attaches the new media to the node's extra attributes.
     *
     * [getResolvedUrl] is used rather than [LinkNode.url] in case a different URL was set by [LinkUrlResolverHook].
     *
     * @param link the link node containing the media to register.
     * It is also the node to attach the [StoredMediaProperty] to, into [org.example.project.qdcore.ast.attributes.AstAttributes.properties]
     */
    private fun register(link: LinkNode) {
        val url = link.getResolvedUrl(context)
        if (MediaPassthrough.isPassthroughPath(url)) {
            Log.debug("Media is a passthrough: ${link.url}")
            return
        }

        val media: StoredMedia? =
            try {
                context.mediaStorage.register(
                    url,
                    context.fileSystem.workingDirectory,
                )
            } catch (_: IllegalArgumentException) {
                Log.warn("Media cannot be resolved: ${link.url}")
                return
            } catch (e: MissingPermissionException) {
                link.setError(e, context)
                return
            }

        // The stored media is attached to the node's extra attributes.
        media
            ?.let(::StoredMediaProperty)
            ?.also { Log.debug("Registered media: ${link.url} -> ${it.value}") }
            ?.let {
                context.attributes.of(link) += it
            }
    }

    override fun attach(iterator: ObservableAstIterator) {
        // Images are instantly registered.
        iterator.on<Image> {
            if (it.usesMediaStorage) {
                register(it.link)
            }
        }

        // Reference images are registered upon resolution,
        // i.e. when a definition that matches the reference is found.
        iterator.on<ReferenceImage> { it.link.onResolve.add(::register) }
    }
}
