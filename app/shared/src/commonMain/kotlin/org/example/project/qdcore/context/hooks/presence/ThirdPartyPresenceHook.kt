package org.example.project.qdcore.context.hooks.presence

import org.example.project.qdcore.ast.Node
import org.example.project.qdcore.ast.attributes.presence.markCodePresence
import org.example.project.qdcore.ast.attributes.presence.markMathPresence
import org.example.project.qdcore.ast.attributes.presence.markMermaidDiagramPresence
import org.example.project.qdcore.ast.base.block.Code
import org.example.project.qdcore.ast.iterator.AstIteratorHook
import org.example.project.qdcore.ast.iterator.ObservableAstIterator
import org.example.project.qdcore.ast.quarkdown.block.Math
import org.example.project.qdcore.ast.quarkdown.block.MermaidDiagram
import org.example.project.qdcore.ast.quarkdown.block.SubdocumentGraph
import org.example.project.qdcore.ast.quarkdown.inline.MathSpan
import org.example.project.qdcore.context.MutableContext

// Hooks that mark the presence of third-party elements in the document,
// in order to conditionally load third-party libraries in the final artifact.

/**
 * Hook that marks the presence of code elements in the [context]'s attributes
 * if at least one [Code] block is present in the document.
 * @see org.example.project.qdcore.ast.attributes.presence.CodePresenceProperty
 */
class CodePresenceHook(
    private val context: MutableContext,
) : AstIteratorHook {
    override fun attach(iterator: ObservableAstIterator) {
        iterator.on<Code> { context.attributes.markCodePresence() }
    }
}

/**
 * Hook that marks the presence of math elements in the [context]'s attributes
 * if at least one [Math] or [MathSpan] block is present in the document.
 * @see org.example.project.qdcore.ast.attributes.presence.MathPresenceProperty
 */
class MathPresenceHook(
    private val context: MutableContext,
) : AstIteratorHook {
    private val action: (Node) -> Unit
        get() = { context.attributes.markMathPresence() }

    override fun attach(iterator: ObservableAstIterator) {
        iterator
            .on<Math>(action)
            .on<MathSpan>(action)
    }
}

/**
 * Hook that marks the presence of code elements in the [context]'s attributes
 * if at least one [Code] block is present in the document.
 * @see org.example.project.qdcore.ast.attributes.presence.MermaidDiagramPresenceProperty
 */
class MermaidDiagramPresenceHook(
    private val context: MutableContext,
) : AstIteratorHook {
    override fun attach(iterator: ObservableAstIterator) {
        iterator.on<MermaidDiagram> { context.attributes.markMermaidDiagramPresence() }
        iterator.on<SubdocumentGraph> { context.attributes.markMermaidDiagramPresence() }
    }
}
