package org.example.project.qdcore.pipeline.stages

import org.example.project.qdcore.pipeline.PipelineHooks
import org.example.project.qdcore.pipeline.stage.PipelineStage
import org.example.project.qdcore.pipeline.stage.SharedPipelineData
import org.example.project.qdcore.rendering.PostRenderer

/**
 * Pipeline stage responsible for post-processing the rendered output.
 *
 * This stage takes a [CharSequence] (produced by the [RenderingStage]) as input and
 * produces a [CharSequence] as output, wrapping the rendered content into a template using a [PostRenderer].
 */
class PostRenderingStage(
    private val postRenderer: PostRenderer,
) : PipelineStage<CharSequence, CharSequence> {
    override val hook = PipelineHooks::afterPostRendering

    override fun process(
        input: CharSequence,
        data: SharedPipelineData,
    ): CharSequence = this.postRenderer.wrap(input)
}
