package org.example.project.qdcore.function.value.output.node

import org.example.project.qdcore.ast.Node
import org.example.project.qdcore.ast.base.block.Paragraph
import org.example.project.qdcore.context.Context
import org.example.project.qdcore.function.value.BooleanValue
import org.example.project.qdcore.function.value.NoneValue
import org.example.project.qdcore.function.value.NumberValue
import org.example.project.qdcore.function.value.ObjectValue
import org.example.project.qdcore.function.value.StringValue
import org.example.project.qdcore.function.value.factory.ValueFactory

/**
 * Producer of block nodes from function output values.
 * @param context context of the function
 * @see NodeOutputValueVisitor
 */
class BlockNodeOutputValueVisitor(
    private val context: Context,
) : NodeOutputValueVisitor() {
    // Proxy used to convert inline values to block values.
    private val inline = InlineNodeOutputValueVisitor(context)

    /**
     * @return [this] node wrapped in a [Paragraph] block
     */
    private fun Node.inParagraph() = Paragraph(listOf(this))

    // Inline-to-block conversion.

    override fun visit(value: StringValue) = inline.visit(value).inParagraph()

    override fun visit(value: NumberValue) = inline.visit(value).inParagraph()

    override fun visit(value: BooleanValue) = inline.visit(value).inParagraph()

    override fun visit(value: ObjectValue<*>) = inline.visit(value).inParagraph()

    override fun visit(value: NoneValue) = inline.visit(value).inParagraph()

    // Raw Markdown code is parsed as blocks.
    override fun parseRaw(
        raw: String,
        context: Context?,
    ) = ValueFactory.blockMarkdown(raw, context ?: this.context).asNodeValue()
}
