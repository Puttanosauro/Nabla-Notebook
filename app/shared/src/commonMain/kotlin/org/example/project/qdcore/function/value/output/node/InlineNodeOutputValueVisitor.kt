package org.example.project.qdcore.function.value.output.node

import org.example.project.qdcore.ast.base.inline.CheckBox
import org.example.project.qdcore.ast.base.inline.CodeSpan
import org.example.project.qdcore.ast.base.inline.Text
import org.example.project.qdcore.context.Context
import org.example.project.qdcore.function.value.BooleanValue
import org.example.project.qdcore.function.value.NoneValue
import org.example.project.qdcore.function.value.NumberValue
import org.example.project.qdcore.function.value.ObjectValue
import org.example.project.qdcore.function.value.StringValue
import org.example.project.qdcore.function.value.factory.ValueFactory

/**
 * Producer of inline nodes from function output values.
 * @param context context of the function
 * @see NodeOutputValueVisitor
 */
class InlineNodeOutputValueVisitor(
    private val context: Context,
) : NodeOutputValueVisitor() {
    override fun visit(value: StringValue) = Text(value.unwrappedValue)

    override fun visit(value: NumberValue) = Text(value.unwrappedValue.toString())

    override fun visit(value: BooleanValue) = CheckBox(isChecked = value.unwrappedValue)

    override fun visit(value: ObjectValue<*>) = Text(value.unwrappedValue.toString())

    override fun visit(value: NoneValue) = CodeSpan(value.unwrappedValue.toString())

    // Raw Markdown code is parsed as inline.
    override fun parseRaw(
        raw: String,
        context: Context?,
    ) = ValueFactory.inlineMarkdown(raw, context ?: this.context).asNodeValue()
}
