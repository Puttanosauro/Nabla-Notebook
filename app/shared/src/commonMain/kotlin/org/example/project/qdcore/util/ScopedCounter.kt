package org.example.project.qdcore.util

/**
 * A thread-safe counter that tracks a depth value within nested scopes.
 */
class ScopedCounter(
    @PublishedApi internal val maxDepth: Int,
    @PublishedApi internal val onOverflow: () -> Nothing,
) {
    // We now use our KMP-friendly AtomicInt!
    @PublishedApi internal val depth: AtomicInt = AtomicInt(0)

    fun get(): Int = depth.get()

    inline fun <T> incrementScoped(block: () -> T): T {
        val current = depth.incrementAndGet()
        if (current > maxDepth) {
            depth.decrementAndGet()
            onOverflow()
        }
        try {
            return block()
        } finally {
            depth.decrementAndGet()
        }
    }
}