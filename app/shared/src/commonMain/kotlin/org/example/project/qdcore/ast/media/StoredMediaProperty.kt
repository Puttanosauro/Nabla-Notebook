package org.example.project.qdcore.ast.media

import org.example.project.qdcore.ast.Node
import org.example.project.qdcore.ast.attributes.AstAttributes
import org.example.project.qdcore.context.Context
import org.example.project.qdcore.media.storage.StoredMedia
import org.example.project.qdcore.property.Property

/**
 * Property that can be attached to a [Node] in [AstAttributes.properties]
 * to signal that the node is bound to a [StoredMedia] resolved by a [org.example.project.qdcore.media.storage.ReadOnlyMediaStorage].
 * @param value the stored media
 * @see StoredMedia
 * @see org.example.project.qdcore.media.storage.ReadOnlyMediaStorage
 * @see org.example.project.qdcore.ast.attributes.AstAttributes.properties
 */
data class StoredMediaProperty(
    override val value: StoredMedia,
) : Property<StoredMedia> {
    companion object : Property.Key<StoredMedia>

    override val key: Property.Key<StoredMedia> = StoredMediaProperty
}

/**
 * Retrieves the stored media associated with [this] node, if any.
 * @param attributes the attributes to extract the properties from
 * @return the stored media associated with [this] node, if any
 */
internal fun Node.getStoredMedia(attributes: AstAttributes): StoredMedia? = attributes.of(this)[StoredMediaProperty]

/**
 * Retrieves the stored media associated with [this] node, if any.
 * @param context the context to extract the properties from
 * @return the stored media associated with [this] node, if any
 */
fun Node.getStoredMedia(context: Context): StoredMedia? = getStoredMedia(context.attributes)
