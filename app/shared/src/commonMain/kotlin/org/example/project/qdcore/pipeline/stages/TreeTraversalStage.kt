package org.example.project.qdcore.pipeline.stages

import org.example.project.qdcore.ast.AstRoot
import org.example.project.qdcore.pipeline.PipelineHooks
import org.example.project.qdcore.pipeline.stage.PeekPipelineStage
import org.example.project.qdcore.pipeline.stage.SharedPipelineData

/**
 * Pipeline stage responsible for traversing the abstract syntax tree (AST).
 *
 * This stage uses a tree iterator to traverse the AST and perform operations on it.
 *
 * @see org.example.project.qdcore.context.hooks for tree traversal hooks.
 */
object TreeTraversalStage : PeekPipelineStage<AstRoot> {
    override val hook = PipelineHooks::afterTreeTraversal

    override fun peek(
        input: AstRoot,
        data: SharedPipelineData,
    ) {
        data.context.flavor.treeIteratorFactory
            .default(data.context)
            .traverse(input)
    }
}
