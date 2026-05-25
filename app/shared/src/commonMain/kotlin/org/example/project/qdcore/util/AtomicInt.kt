package org.example.project.qdcore.util

/**
 * A multiplatform abstraction for a thread-safe integer.
 */

/**
 * we use this to work around the JVM without losing functionality on wasm.
*/
expect class AtomicInt(initialValue: Int) {
    fun get(): Int
    fun incrementAndGet(): Int
    fun decrementAndGet(): Int
}