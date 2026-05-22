package org.example.project.qdcore.ast.dsl

import org.example.project.qdcore.ast.base.block.list.ListItem
import org.example.project.qdcore.ast.base.block.list.ListItemVariant

/**
 * A builder of list items.
 * @see BlockAstBuilder.orderedList
 * @see BlockAstBuilder.unorderedList
 */
class ListAstBuilder : AstBuilder() {
    /**
     * @see ListItem
     */
    fun listItem(
        vararg variants: ListItemVariant,
        block: BlockAstBuilder.() -> Unit,
    ) = node(ListItem(variants.toList(), buildBlocks(block)))
}
