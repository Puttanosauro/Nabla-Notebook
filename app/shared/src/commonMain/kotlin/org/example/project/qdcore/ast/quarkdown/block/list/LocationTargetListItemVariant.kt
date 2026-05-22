package org.example.project.qdcore.ast.quarkdown.block.list

import org.example.project.qdcore.ast.attributes.location.LocationTrackableNode
import org.example.project.qdcore.ast.base.block.list.ListItem
import org.example.project.qdcore.ast.base.block.list.ListItemVariant
import org.example.project.qdcore.ast.base.block.list.ListItemVariantVisitor
import org.example.project.qdcore.document.numbering.DocumentNumbering
import org.example.project.qdcore.document.numbering.NumberingFormat

/**
 * Variant of a [ListItem] that displays the location of a target node,
 * usually (rendering-dependent) by replacing the item marker with [target]'s position,
 * formatted according to the global numbering format.
 * This is used, for example, in table of contents.
 * @param target node to display the location of
 * @param format kind of numbering format to use to format the location
 */
data class LocationTargetListItemVariant(
    val target: LocationTrackableNode,
    val format: (DocumentNumbering) -> NumberingFormat?,
) : ListItemVariant {
    override fun <T> accept(visitor: ListItemVariantVisitor<T>): T = visitor.visit(this)
}
