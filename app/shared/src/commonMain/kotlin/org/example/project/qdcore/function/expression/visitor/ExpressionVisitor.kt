package org.example.project.qdcore.function.expression.visitor

import org.example.project.qdcore.function.call.FunctionCall
import org.example.project.qdcore.function.expression.ComposedExpression
import org.example.project.qdcore.function.expression.Expression
import org.example.project.qdcore.function.value.BooleanValue
import org.example.project.qdcore.function.value.DictionaryValue
import org.example.project.qdcore.function.value.DynamicValue
import org.example.project.qdcore.function.value.EnumValue
import org.example.project.qdcore.function.value.GeneralCollectionValue
import org.example.project.qdcore.function.value.InlineMarkdownContentValue
import org.example.project.qdcore.function.value.LambdaValue
import org.example.project.qdcore.function.value.MarkdownContentValue
import org.example.project.qdcore.function.value.NodeValue
import org.example.project.qdcore.function.value.NoneValue
import org.example.project.qdcore.function.value.NumberValue
import org.example.project.qdcore.function.value.ObjectValue
import org.example.project.qdcore.function.value.OrderedCollectionValue
import org.example.project.qdcore.function.value.PairValue
import org.example.project.qdcore.function.value.StringValue
import org.example.project.qdcore.function.value.UnorderedCollectionValue

/**
 * A visitor for different kinds of [Expression].
 * @param T output type of the `visit` methods
 * @see Expression
 * @see EvalExpressionVisitor
 * @see AppendExpressionVisitor
 */
interface ExpressionVisitor<T> {
    fun visit(value: StringValue): T

    fun visit(value: NumberValue): T

    fun visit(value: BooleanValue): T

    fun visit(value: OrderedCollectionValue<*>): T

    fun visit(value: UnorderedCollectionValue<*>): T

    fun visit(value: GeneralCollectionValue<*>): T

    fun visit(value: PairValue<*, *>): T

    fun visit(value: DictionaryValue<*>): T

    fun visit(value: EnumValue): T

    fun visit(value: ObjectValue<*>): T

    fun visit(value: MarkdownContentValue): T

    fun visit(value: InlineMarkdownContentValue): T

    fun visit(value: NodeValue): T

    fun visit(value: DynamicValue): T

    fun visit(value: LambdaValue): T

    fun visit(value: NoneValue): T

    fun visit(expression: FunctionCall<*>): T

    fun visit(expression: ComposedExpression): T
}
