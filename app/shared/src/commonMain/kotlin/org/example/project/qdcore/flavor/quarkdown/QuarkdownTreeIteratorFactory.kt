package org.example.project.qdcore.flavor.quarkdown

import org.example.project.qdcore.ast.iterator.ObservableAstIterator
import org.example.project.qdcore.context.MutableContext
import org.example.project.qdcore.context.hooks.MediaStorerHook
import org.example.project.qdcore.context.hooks.TableOfContentsGeneratorHook
import org.example.project.qdcore.context.hooks.location.LocationAwareLabelStorerHook
import org.example.project.qdcore.context.hooks.location.LocationAwarenessHook
import org.example.project.qdcore.context.hooks.location.NumberedEvaluatorHook
import org.example.project.qdcore.context.hooks.reference.BibliographyCitationResolverHook
import org.example.project.qdcore.context.hooks.reference.CrossReferenceResolverHook
import org.example.project.qdcore.flavor.TreeIteratorFactory
import org.example.project.qdcore.flavor.base.BaseMarkdownTreeIteratorFactory

/**
 * [QuarkdownFlavor] tree iterator factory.
 */
class QuarkdownTreeIteratorFactory : TreeIteratorFactory {
    override fun default(context: MutableContext): ObservableAstIterator =
        BaseMarkdownTreeIteratorFactory()
            .default(context)
            .attach(LocationAwarenessHook(context))
            .attach(LocationAwareLabelStorerHook(context))
            .attach(NumberedEvaluatorHook(context))
            .attach(TableOfContentsGeneratorHook(context))
            .attach(CrossReferenceResolverHook(context))
            .attach(BibliographyCitationResolverHook(context))
            .apply {
                if (context.attachedPipeline?.options?.enableMediaStorage == true) {
                    attach(MediaStorerHook(context))
                }
            }
}
