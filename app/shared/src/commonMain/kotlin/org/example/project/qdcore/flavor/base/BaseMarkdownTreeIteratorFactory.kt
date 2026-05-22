package org.example.project.qdcore.flavor.base

import org.example.project.qdcore.ast.iterator.ObservableAstIterator
import org.example.project.qdcore.context.MutableContext
import org.example.project.qdcore.context.hooks.LinkUrlResolverHook
import org.example.project.qdcore.context.hooks.SubdocumentRegistrationHook
import org.example.project.qdcore.context.hooks.presence.CodePresenceHook
import org.example.project.qdcore.context.hooks.presence.MathPresenceHook
import org.example.project.qdcore.context.hooks.presence.MermaidDiagramPresenceHook
import org.example.project.qdcore.context.hooks.reference.FootnoteResolverHook
import org.example.project.qdcore.context.hooks.reference.LinkDefinitionResolverHook
import org.example.project.qdcore.flavor.TreeIteratorFactory

/**
 * [BaseMarkdownFlavor] tree iterator factory.
 */
class BaseMarkdownTreeIteratorFactory : TreeIteratorFactory {
    override fun default(context: MutableContext): ObservableAstIterator =
        ObservableAstIterator()
            // Resolves reference links to their link definitions.
            .attach(LinkDefinitionResolverHook(context))
            // Registers subdocuments.
            .attach(SubdocumentRegistrationHook(context))
            // Resolves local URLs/paths for links and images loaded from different base paths.
            .attach(LinkUrlResolverHook(context))
            // Resolves footnotes.
            .attach(FootnoteResolverHook(context))
            // Allows loading code libraries (e.g. highlight.js syntax highlighting)
            // if at least one code block is present.
            .attach(CodePresenceHook(context))
            // Allows loading Mermaid libraries
            // if at least one diagram is present.
            .attach(MermaidDiagramPresenceHook(context))
            // Allows loading math libraries (e.g. KaTeX)
            // if at least one math block is present.
            .attach(MathPresenceHook(context))
}
