package org.example.project.qdcore.util

import java.util.concurrent.atomic.AtomicInteger

actual class AtomicInt actual constructor(initialValue: Int) {
    private val delegate = AtomicInteger(initialValue)

    actual fun get(): Int = delegate.get()
    actual fun incrementAndGet(): Int = delegate.incrementAndGet()
    actual fun decrementAndGet(): Int = delegate.decrementAndGet()
}