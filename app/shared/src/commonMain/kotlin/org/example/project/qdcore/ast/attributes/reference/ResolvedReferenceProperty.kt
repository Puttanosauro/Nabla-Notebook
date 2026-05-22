package org.example.project.qdcore.ast.attributes.reference

import org.example.project.qdcore.property.Property

/**
 * A pair of a referenced linked to its resolved definition.
 */
typealias ResolvedReference<R, D> = Pair<R, D>

/**
 * [Property] that can be assigned to each [ReferenceNode]. It contains the definition that the reference refers to.
 * @see ReferenceNode
 * @see org.example.project.qdcore.context.hooks.reference.ReferenceDefinitionResolverHook for the assignment stage
 */
data class ResolvedReferenceProperty<R, D>(
    override val value: ResolvedReference<R, D>,
) : Property<ResolvedReference<R, D>> {
    class Key<R, D> : Property.Key<ResolvedReference<R, D>> {
        override fun equals(other: Any?): Boolean = other is Key<*, *>

        override fun hashCode(): Int = this::class.hashCode()
    }

    override val key = Key<R, D>()
}
