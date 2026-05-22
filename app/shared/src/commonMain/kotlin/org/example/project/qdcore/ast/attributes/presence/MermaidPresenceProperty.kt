package org.example.project.qdcore.ast.attributes.presence

import org.example.project.qdcore.ast.attributes.AstAttributes
import org.example.project.qdcore.ast.attributes.MutableAstAttributes
import org.example.project.qdcore.property.Property

/**
 * If this property is present in [org.example.project.qdcore.ast.attributes.AstAttributes.thirdPartyPresenceProperties]
 * and its [value] is true, it means there is at least one Mermaid diagram in the AST.
 * This is used to load the Mermaid library in HTML rendering only if necessary.
 * @see org.example.project.qdcore.context.hooks.presence.MermaidDiagramPresenceHook
 */
data class MermaidDiagramPresenceProperty(
    override val value: Boolean,
) : Property<Boolean> {
    companion object : Property.Key<Boolean>

    override val key: Property.Key<Boolean> = MermaidDiagramPresenceProperty
}

/**
 * Whether there is at least one Mermaid diagram in the AST.
 * @see MermaidDiagramPresenceProperty
 */
val AstAttributes.hasMermaidDiagram: Boolean
    get() = hasThirdParty(MermaidDiagramPresenceProperty)

/**
 * Marks the presence of Mermaid diagrams in the AST
 * if at least one diagram is present in the document.
 * @see MermaidDiagramPresenceProperty
 * @see org.example.project.qdcore.context.hooks.presence.MermaidDiagramPresenceHook
 */
fun MutableAstAttributes.markMermaidDiagramPresence() = markThirdPartyPresence(MermaidDiagramPresenceProperty(true))
