package org.example.project.qdcore.pipeline

import org.example.project.qdcore.pipeline.output.OutputResource
import org.example.project.qdcore.pipeline.stage.PipelineStage
import org.example.project.qdcore.pipeline.stage.then
import org.example.project.qdcore.pipeline.stage.thenOptionally
import org.example.project.qdcore.pipeline.stages.AfterAllRenderingPeek
import org.example.project.qdcore.pipeline.stages.AttachmentStage
import org.example.project.qdcore.pipeline.stages.AttributesUpdateStage
import org.example.project.qdcore.pipeline.stages.FunctionCallExpansionStage
import org.example.project.qdcore.pipeline.stages.LexingStage
import org.example.project.qdcore.pipeline.stages.LibrariesRegistrationStage
import org.example.project.qdcore.pipeline.stages.ParsingStage
import org.example.project.qdcore.pipeline.stages.PostRenderingStage
import org.example.project.qdcore.pipeline.stages.RenderingStage
import org.example.project.qdcore.pipeline.stages.ResourceGenerationStage
import org.example.project.qdcore.pipeline.stages.TreeTraversalStage
import org.example.project.qdcore.rendering.RenderingComponents

/**
 * Factory for creating standard pipeline stage chains.
 */
object PipelineChainFactory {
    /**
     * Creates a full pipeline stage chain that processes the input source text
     * through all stages, up to resource generation.
     *
     * @param source the raw input text to be processed
     * @param renderingComponents the rendering components to use in the rendering stages
     * @return a pipeline stage that processes the input source text and produces output resources
     */
    fun fullChain(
        source: CharSequence,
        renderingComponents: RenderingComponents,
        options: PipelineOptions,
    ): PipelineStage<Unit, Set<OutputResource>> =
        AttachmentStage then
            LibrariesRegistrationStage then
            LexingStage(source) then
            ParsingStage then
            AttributesUpdateStage(preferredMediaStorageOptions = renderingComponents.postRenderer.preferredMediaStorageOptions) then
            FunctionCallExpansionStage then
            TreeTraversalStage then
            RenderingStage(renderingComponents.nodeRenderer) thenOptionally
            PostRenderingStage(renderingComponents.postRenderer).takeIf { options.wrapOutput } then
            AfterAllRenderingPeek then
            ResourceGenerationStage(renderingComponents.postRenderer)
}
