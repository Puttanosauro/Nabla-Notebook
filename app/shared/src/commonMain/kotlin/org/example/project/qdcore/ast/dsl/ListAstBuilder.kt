package org.example.project.qdcore.ast.dsl

import org.example.project.qdcore.ast.base.block.list.ListItem
import org.example.project.qdcore.ast.base.block.list.ListItemVariant

class ListAstBuilder : AstBuilder() {
    fun listItem(
        vararg variants: ListItemVariant,
        block: BlockAstBuilder.() -> Unit,
    ) = node(ListItem(listOf(*variants), buildBlocks(block))) // HACKED: Used listOf(*...) instead of .toList()
}