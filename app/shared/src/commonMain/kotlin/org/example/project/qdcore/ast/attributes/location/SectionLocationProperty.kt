package org.example.project.qdcore.ast.attributes.location

import org.example.project.qdcore.property.Property

/**
 * [Property] that is assigned to each node that requests its location to be tracked ([LocationTrackableNode]).
 * It contains the node's location in the document, in terms of section indices.
 * @see SectionLocation
 * @see org.example.project.qdcore.context.hooks.location.LocationAwarenessHook for the storing stage
 */
data class SectionLocationProperty(
    override val value: SectionLocation,
) : Property<SectionLocation> {
    companion object : Property.Key<SectionLocation>

    override val key = SectionLocationProperty
}
