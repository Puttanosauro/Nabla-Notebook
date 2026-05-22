package org.example.project.qdcore.pipeline.stages

import org.example.project.qdcore.ast.AstRoot
import org.example.project.qdcore.ast.Node
import org.example.project.qdcore.lexer.Token
import org.example.project.qdcore.lexer.acceptAll
import org.example.project.qdcore.pipeline.PipelineHooks
import org.example.project.qdcore.pipeline.stage.PipelineStage
import org.example.project.qdcore.pipeline.stage.SharedPipelineData
import org.example.project.qdcore.visitor.token.TokenVisitor

/**
 * Pipeline stage responsible for parsing tokens into an abstract syntax tree (AST).
 *
 * This stage takes a sequence of tokens (produced by the [LexingStage]) as input and
 * produces an [AstRoot] as output.
 *
 * The AST represents the hierarchical structure of the document and is used by
 * subsequent stages for further processing and rendering.
 */
object ParsingStage : PipelineStage<Sequence<Token>, AstRoot> {
    override val hook = PipelineHooks::afterParsing

    override fun process(
        input: Sequence<Token>,
        data: SharedPipelineData,
    ): AstRoot {
        val parser: TokenVisitor<Node> =
            data.context.flavor.parserFactory
                .newParser(data.context)

        return AstRoot(children = input.acceptAll(parser))
    }
}
