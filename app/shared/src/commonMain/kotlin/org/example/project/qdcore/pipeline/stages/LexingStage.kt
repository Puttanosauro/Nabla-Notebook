package org.example.project.qdcore.pipeline.stages

import org.example.project.qdcore.function.library.Library
import org.example.project.qdcore.lexer.Token
import org.example.project.qdcore.pipeline.PipelineHooks
import org.example.project.qdcore.pipeline.stage.PipelineStage
import org.example.project.qdcore.pipeline.stage.SharedPipelineData

/**
 * Pipeline stage responsible for lexical analysis (tokenization) of the input text.
 *
 * This stage produces a lazy sequence of tokens as output from the provided source text.
 * It uses the lexer factory from the context's flavor to create a block lexer that
 * processes the source text and breaks it down into tokens.
 *
 * The tokens produced by this stage are used by the [ParsingStage] to build an abstract syntax tree.
 *
 * @param source the raw input text to be tokenized
 */
class LexingStage(
    private val source: CharSequence,
) : PipelineStage<Set<Library>, Sequence<Token>> {
    override val hook = PipelineHooks::afterLexing

    override fun process(
        input: Set<Library>,
        data: SharedPipelineData,
    ): Sequence<Token> =
        data.context.flavor.lexerFactory
            .newBlockLexer(this.source)
            .tokenize()
}
