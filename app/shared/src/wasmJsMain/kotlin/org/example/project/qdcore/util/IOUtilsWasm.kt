package org.example.project.qdcore.util

actual fun computeChecksum(path: String): String {
    // WebAssembly has no physical file system to hash.
    // In a virtual environment, document edits are tracked by the CRDT (Yjs) state.
    // Returning a constant or simple string allows the parser to compile without throwing IO exceptions.
    return "WASM_VIRTUAL_STATE_${path.hashCode()}"
}