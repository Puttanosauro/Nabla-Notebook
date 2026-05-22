package org.example.project.qdcore.ast.quarkdown.block.list

import org.example.project.qdcore.ast.base.block.list.ListItem
import org.example.project.qdcore.ast.base.block.list.ListItemVariant
import org.example.project.qdcore.ast.base.block.list.ListItemVariantVisitor
import org.example.project.qdcore.context.toc.TableOfContents

/**
 * A list item variant that associates a [ListItem] to an item of a [TableOfContents],
 * such as a [org.example.project.qdcore.ast.base.block.Heading].
 * @param item the ToC item associated with the list item
 */
data class TableOfContentsItemVariant(
    val item: TableOfContents.Item,
) : ListItemVariant {
    override fun <T> accept(visitor: ListItemVariantVisitor<T>) = visitor.visit(this)
}
