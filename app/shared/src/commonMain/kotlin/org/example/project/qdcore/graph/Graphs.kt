@file:Suppress("FunctionName")

package org.example.project.qdcore.graph

/**
 * @return a new empty directed [Graph]
 */
fun <T> DirectedGraph(): Graph<T> = PersistentDirectedGraph()
