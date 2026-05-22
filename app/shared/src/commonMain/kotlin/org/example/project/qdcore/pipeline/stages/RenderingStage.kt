package org.example.project.qdcore.pipeline.stages

import org.example.project.qdcore.ast.AstRoot
import org.example.project.qdcore.pipeline.PipelineHooks
import org.example.project.qdcore.pipeline.stage.PipelineStage
import org.example.project.qdcore.pipeline.stage.SharedPipelineData
import org.example.project.qdcore.rendering.NodeRenderer

/**
 * Pipeline stage responsible for rendering the abstract syntax tree (AST) into a text output.
 *
 * This stage takes an [AstRoot] (produced by the [ParsingStage]) as input and
 * produces a [CharSequence] as output. It uses a [NodeRenderer] to visit the AST
 * and render it into the target format (e.g., HTML).
 */
class RenderingStage(
    private val renderer: NodeRenderer,
) : PipelineStage<AstRoot, CharSequence> {
    override val hook = PipelineHooks::afterRendering

    override fun process(
        input: AstRoot,
        data: SharedPipelineData,
    ): CharSequence = this.renderer.visit(input)
}
