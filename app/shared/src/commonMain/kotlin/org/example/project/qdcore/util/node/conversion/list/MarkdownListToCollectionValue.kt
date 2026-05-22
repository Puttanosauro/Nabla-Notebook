package org.example.project.qdcore.util.node.conversion.list

import org.example.project.qdcore.ast.Node
import org.example.project.qdcore.ast.base.TextNode
import org.example.project.qdcore.ast.base.block.list.ListBlock
import org.example.project.qdcore.context.Context
import org.example.project.qdcore.function.value.NodeValue
import org.example.project.qdcore.function.value.OrderedCollectionValue
import org.example.project.qdcore.function.value.OutputValue
import org.example.project.qdcore.function.value.factory.ValueFactory
import org.example.project.qdcore.util.node.toPlainText

/**
 * Helper that converts a Markdown list to an [OrderedCollectionValue].
 * @param list list to convert
 * @param inlineValueMapper function that maps the node of a list item to a value
 * @param nestedValueMapper function that maps a nested list item to a value.
 *        The first argument is the parent node, and the second is the nested [ListBlock]
 * @param T type of values in the collection
 * @see OrderedCollectionValue
 * @see ValueFactory.iterable
 */
class MarkdownListToCollectionValue<T : OutputValue<*>>(
    list: ListBlock,
    inlineValueMapper: (Node) -> T,
    nestedValueMapper: (Node, ListBlock) -> T,
) : MarkdownListToIterable<OrderedCollectionValue<T>, T>(list, inlineValueMapper, nestedValueMapper) {
    override fun wrap(): OrderedCollectionValue<T> = OrderedCollectionValue(elements.toList())

    companion object {
        /**
         * [MarkdownListToCollectionValue] factory via a [ValueFactory].
         * @param list list to convert
         * @param context context to use for the conversion
         */
        fun viaValueFactory(
            list: ListBlock,
            context: Context,
        ): MarkdownListToCollectionValue<*> =
            MarkdownListToCollectionValue(
                list,
                inlineValueMapper = {
                    when (it) {
                        is TextNode -> ValueFactory.eval(it.text.toPlainText(), context)
                        else -> NodeValue(it)
                    }
                },
                nestedValueMapper = { _, list -> viaValueFactory(list, context).convert() },
            )
    }
}
