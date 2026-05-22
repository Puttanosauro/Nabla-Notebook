package org.example.project.qdcore.ast.base.block.list

import org.example.project.qdcore.ast.quarkdown.block.list.FocusListItemVariant
import org.example.project.qdcore.ast.quarkdown.block.list.LocationTargetListItemVariant
import org.example.project.qdcore.ast.quarkdown.block.list.TableOfContentsItemVariant

/**
 * A variant of a [ListItem] that brings additional functionalities to it.
 */
interface ListItemVariant {
    /**
     * Accepts a [ListItemVariantVisitor].
     * @param visitor visitor to accept
     * @return result of the visit operation
     */
    fun <T> accept(visitor: ListItemVariantVisitor<T>): T
}

/**
 * Visitor of [ListItemVariant]s.
 * @param T return type of the visit operations
 */
interface ListItemVariantVisitor<T> {
    fun visit(variant: TaskListItemVariant): T

    fun visit(variant: FocusListItemVariant): T

    fun visit(variant: LocationTargetListItemVariant): T

    fun visit(variant: TableOfContentsItemVariant): T
}
