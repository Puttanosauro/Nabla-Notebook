package org.example.project.qdcore.ast.attributes.presence

import org.example.project.qdcore.ast.attributes.AstAttributes
import org.example.project.qdcore.ast.attributes.MutableAstAttributes
import org.example.project.qdcore.property.Property

/**
 * @return whether the [AstAttributes] contain a third-party presence property with the given [key]
 */
internal fun AstAttributes.hasThirdParty(key: Property.Key<Boolean>): Boolean = thirdPartyPresenceProperties[key] == true

/**
 * Marks the presence of a third-party element in the AST via the given [property].
 */
internal fun MutableAstAttributes.markThirdPartyPresence(property: Property<Boolean>) {
    thirdPartyPresenceProperties += property
}
