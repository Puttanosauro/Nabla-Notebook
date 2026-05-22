package org.example.project.qdcore.util.node.conversion.list

import org.example.project.qdcore.ast.Node
import org.example.project.qdcore.ast.base.block.Newline
import org.example.project.qdcore.ast.base.block.list.ListBlock
import org.example.project.qdcore.function.value.factory.IllegalRawValueException

/**
 * Helper that converts a Markdown list to a value of type [T].
 * @param list list to convert
 * @param T type of value to convert to
 * @param E type of elements that compose the output value
 * @param N type of nodes, children of the list, that can be handled
 */
abstract class MarkdownListConverter<T, E, N : Node>(
    private val list: ListBlock,
) {
    /**
     * Pushes an [element] to the internal value.
     */
    protected abstract fun push(element: E)

    /**
     * Validates the first child of a list item and converts it to a subclass of type [N].
     * @param firstChild the first child of a list item
     * @return the validated child, converted to a [Node] subclass of type [N]
     * @throws IllegalRawValueException if the child is not valid
     */
    protected abstract fun validateChild(firstChild: Node): N

    /**
     * Converts the inline child of a list item to a pushable element.
     * "Inline" means the list item does not contain a nested list, for example:
     * ```
     * - Inline 1
     * - Inline 2
     * - Nested
     *   - Inline 3
     * ```
     * @param child the first child of a list item. In the previous example, it would be a paragraph which contains "Inline 1", "Inline 2" or "Inline 3".
     * @return the element to push
     */
    protected abstract fun inlineValue(child: N): E

    /**
     * Converts the nested child of a list item to a pushable element.
     * ```
     * - Nested
     *   - A
     *   - B
     *   - C
     * ```
     * @param child the first child of a list item. In the previous example, it would be a paragraph which contains "Nested".
     * @param list the Markdown nested list. In the previous example, it would be a list which contains "A", "B" and "C".
     * @return the element to push
     */
    protected abstract fun nestedValue(
        child: N,
        list: ListBlock,
    ): E

    /**
     * Wraps the pushed elements into a value of type [T].
     * @return the wrapped value
     */
    protected abstract fun wrap(): T

    /**
     * @return [list] converted to a value of type [T]
     * @throws IllegalRawValueException if the list is not in the correct format
     */
    fun convert(): T {
        list.items
            .asSequence()
            .map { it.children.filterNot { child -> child is Newline } }
            .forEach { children ->
                val firstChild: N = validateChild(children.first())
                when (val secondChild = children.getOrNull(1)) {
                    null -> push(inlineValue(firstChild))
                    is ListBlock -> push(nestedValue(firstChild, secondChild))
                    else -> throw IllegalRawValueException("Unexpected element", secondChild)
                }
            }
        return wrap()
    }
}
