package org.example.project.qdcore.pipeline.output

actual fun readInternalResourceBytes(resource: String): List<Byte>? {
    // TODO (JVM/Desktop): Use Java's ClassLoader to read the bundled file from the .jar
    // This is synchronous and has access to the physical package.
    //
    // Implementation hint:
    // return LazyOutputArtifact::class.java.getResource(resource)?.readBytes()?.toList()

    return null
}