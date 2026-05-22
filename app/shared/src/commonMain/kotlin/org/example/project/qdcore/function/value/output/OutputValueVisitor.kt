package org.example.project.qdcore.function.value.output

import org.example.project.qdcore.function.value.BooleanValue
import org.example.project.qdcore.function.value.DictionaryValue
import org.example.project.qdcore.function.value.DynamicValue
import org.example.project.qdcore.function.value.GeneralCollectionValue
import org.example.project.qdcore.function.value.NodeValue
import org.example.project.qdcore.function.value.NoneValue
import org.example.project.qdcore.function.value.NumberValue
import org.example.project.qdcore.function.value.ObjectValue
import org.example.project.qdcore.function.value.OrderedCollectionValue
import org.example.project.qdcore.function.value.PairValue
import org.example.project.qdcore.function.value.StringValue
import org.example.project.qdcore.function.value.UnorderedCollectionValue
import org.example.project.qdcore.function.value.VoidValue

/**
 * A visitor that produces values the same type for each [org.example.project.qdcore.function.value.OutputValue] type.
 */
interface OutputValueVisitor<T> {
    fun visit(value: StringValue): T

    fun visit(value: NumberValue): T

    fun visit(value: BooleanValue): T

    fun visit(value: ObjectValue<*>): T

    fun visit(value: OrderedCollectionValue<*>): T

    fun visit(value: UnorderedCollectionValue<*>): T

    fun visit(value: GeneralCollectionValue<*>): T

    fun visit(value: PairValue<*, *>): T

    fun visit(value: DictionaryValue<*>): T

    fun visit(value: NodeValue): T

    fun visit(value: NoneValue): T

    fun visit(value: VoidValue): T

    fun visit(value: DynamicValue): T
}
