package org.example.project.qdcore.function.error.internal

import org.example.project.qdcore.function.expression.ComposedExpression
import org.example.project.qdcore.function.expression.Expression
import org.example.project.qdcore.function.value.NodeValue
import org.example.project.qdcore.function.value.factory.ValueFactory

/**
 * An exception thrown when an [Expression] cannot be evaluated.
 * Most commonly, this is thrown when a [NodeValue] appears in a [ComposedExpression],
 * hence the content must be parsed as Markdown instead of expression.
 * @see ValueFactory.eval
 */
class InvalidExpressionEvalException : Exception()
