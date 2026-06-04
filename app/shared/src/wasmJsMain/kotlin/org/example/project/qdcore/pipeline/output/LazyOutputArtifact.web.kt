package org.example.project.qdcore.pipeline.output

actual fun readInternalResourceBytes(resource: String): List<Byte>? {
    // TODO (Web Browser): Browsers do not have a ClassLoader or synchronous file reading.
    // To satisfy this synchronously, you must PRE-LOAD the required assets (like CSS/Fonts)
    // into a global Kotlin Map<String, ByteArray> before the pipeline starts running.
    //
    // Implementation hint:
    // return GlobalAssetCache.get(resource)?.toList()

    return null
}