package org.example.project.qdcore.visitor.token

/**
 * A visitor for [org.example.project.qdcore.lexer.Token]s.
 * @param T output type of the `visit` methods
 */
interface TokenVisitor<T> :
    BlockTokenVisitor<T>,
    InlineTokenVisitor<T>
