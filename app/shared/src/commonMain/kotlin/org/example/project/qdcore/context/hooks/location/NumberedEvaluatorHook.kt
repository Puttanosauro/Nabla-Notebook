package org.example.project.qdcore.context.hooks.location

import org.example.project.qdcore.ast.attributes.error.setError
import org.example.project.qdcore.ast.attributes.location.getLocationLabel
import org.example.project.qdcore.ast.iterator.AstIteratorHook
import org.example.project.qdcore.ast.iterator.ObservableAstIterator
import org.example.project.qdcore.ast.quarkdown.block.Numbered
import org.example.project.qdcore.context.Context
import org.example.project.qdcore.pipeline.error.PipelineException

/**
 * Hook that evaluates the [Numbered] nodes in the document.
 * If the evaluation fails, it attaches an error box, as in a regular function call expansion.
 * This needs to be attached **after** the [LocationAwareLabelStorerHook] has populated the location labels.
 * @param context context to retrieve the location label from
 * @see Numbered to understand why it needs evaluation
 */
class NumberedEvaluatorHook(
    private val context: Context,
) : AstIteratorHook {
    override fun attach(iterator: ObservableAstIterator) {
        iterator.on<Numbered> { node ->
            val label = node.getLocationLabel(context) ?: ""

            try {
                node.children = node.childrenSupplier(label)
            } catch (e: PipelineException) {
                node.setError(e, context)
            }
        }
    }
}
