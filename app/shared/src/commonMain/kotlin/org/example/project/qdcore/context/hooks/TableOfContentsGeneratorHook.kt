package org.example.project.qdcore.context.hooks

import org.example.project.qdcore.ast.base.block.Heading
import org.example.project.qdcore.ast.iterator.AstIteratorHook
import org.example.project.qdcore.ast.iterator.ObservableAstIterator
import org.example.project.qdcore.context.MutableContext
import org.example.project.qdcore.context.toc.TableOfContents

/**
 * Hook that allows the generation of a [TableOfContents] by iterating through [Heading]s.
 * The [TableOfContents] is stored in the [context]'s [MutableContext.attributes] at the end of the traversal.
 */
class TableOfContentsGeneratorHook(
    private val context: MutableContext,
) : AstIteratorHook {
    override fun attach(iterator: ObservableAstIterator) {
        val headings = iterator.collect<Heading> { !it.excludeFromTableOfContents }

        // Generation.
        iterator.onFinished {
            context.attributes.tableOfContents = TableOfContents.generate(headings.asSequence())
        }
    }
}
