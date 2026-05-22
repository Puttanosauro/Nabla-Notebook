package org.example.project.qdcore.ast.base.inline

import org.example.project.qdcore.ast.base.LinkNode
import org.example.project.qdcore.ast.base.TextNode
import org.example.project.qdcore.context.Context
import org.example.project.qdcore.context.MutableContext
import org.example.project.qdcore.document.sub.Subdocument
import org.example.project.qdcore.pipeline.error.PipelineErrorHandler
import org.example.project.qdcore.property.Property
import org.example.project.qdcore.visitor.node.NodeVisitor

/**
 * A link to a Quarkdown subdocument.
 * @param link the link to the subdocument
 * @param anchor an optional anchor to a specific section within the subdocument
 */
class SubdocumentLink(
    val link: Link,
    val anchor: String? = null,
) : LinkNode,
    TextNode {
    override val label by link::label
    override val url by link::url
    override val title by link::title
    override val fileSystem by link::fileSystem
    override val text by link::text

    override var error: Pair<Throwable, PipelineErrorHandler>? = null

    override fun <T> acceptOnSuccess(visitor: NodeVisitor<T>) = visitor.visit(this)

    override fun copy(url: String) =
        SubdocumentLink(
            link = link.copy(url = url),
            anchor = anchor,
        )
}

/**
 * A property that holds a reference to a [Subdocument] associated with a [SubdocumentLink]
 * during the tree traversal stage.
 * @see org.example.project.qdcore.context.hooks.SubdocumentRegistrationHook for the registration stage
 */
data class SubdocumentProperty(
    override val value: Subdocument,
) : Property<Subdocument> {
    companion object : Property.Key<Subdocument>

    override val key: Property.Key<Subdocument> = SubdocumentProperty
}

/**
 * @returns the [Subdocument] associated with this [SubdocumentLink] in the given [context], if any
 */
fun SubdocumentLink.getSubdocument(context: Context): Subdocument? = context.attributes.of(this)[SubdocumentProperty]

/**
 * Associates a [Subdocument] with the [SubdocumentLink] in the given [context].
 * @param context context where subdocument data is stored
 * @param subdocument the subdocument to set
 * @see org.example.project.qdcore.context.hooks.SubdocumentRegistrationHook for the registration stage
 */
fun SubdocumentLink.setSubdocument(
    context: MutableContext,
    subdocument: Subdocument,
) {
    context.attributes.of(this) += SubdocumentProperty(subdocument)
}
