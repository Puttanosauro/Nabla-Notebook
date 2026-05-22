package org.example.project.qdcore.function.expression.visitor

import org.example.project.qdcore.function.call.FunctionCall
import org.example.project.qdcore.function.expression.ComposedExpression
import org.example.project.qdcore.function.expression.append
import org.example.project.qdcore.function.expression.eval
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
import org.example.project.qdcore.function.value.Value

/**
 * An [ExpressionVisitor] that evaluates an expression into a single static value,
 * which can be used as an input for another function call.
 */
class EvalExpressionVisitor : ExpressionVisitor<Value<*>> {
    // Static values: the evaluation is the value itself.
    override fun visit(value: StringValue) = value

    override fun visit(value: NumberValue) = value

    override fun visit(value: BooleanValue) = value

    override fun visit(value: OrderedCollectionValue<*>) = value

    override fun visit(value: UnorderedCollectionValue<*>) = value

    override fun visit(value: GeneralCollectionValue<*>) = value

    override fun visit(value: PairValue<*, *>) = value

    override fun visit(value: DictionaryValue<*>) = value

    override fun visit(value: EnumValue) = value

    override fun visit(value: ObjectValue<*>) = value

    override fun visit(value: MarkdownContentValue) = value

    override fun visit(value: InlineMarkdownContentValue) = value

    override fun visit(value: NodeValue) = value

    override fun visit(value: DynamicValue) = value

    override fun visit(value: LambdaValue) = value

    override fun visit(value: NoneValue) = value

    // When used as an input value for another function call,
    // the output type of the function call must be an InputValue.
    override fun visit(expression: FunctionCall<*>) = expression.execute()

    override fun visit(expression: ComposedExpression): Value<*> {
        if (expression.expressions.isEmpty()) {
            throw IllegalStateException("Composed expression has no sub-expressions")
        }

        // Creates a single expression out of multiple ones
        // by appending them to each other.
        var merged = expression.expressions.first()
        expression.expressions.asSequence().drop(1).forEach {
            merged = merged.append(it)
        }

        // The value of the built expression.
        return merged.eval()
    }
}
