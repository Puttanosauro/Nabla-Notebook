package org.example.project.qdcore.util

actual class AtomicInt actual constructor(private var value: Int) {
    actual fun get(): Int = value
    actual fun incrementAndGet(): Int = ++value
    actual fun decrementAndGet(): Int = --value
}