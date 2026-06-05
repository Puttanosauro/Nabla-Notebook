i replaced most of the original classes with ones with limited capabilities to avoid java stuff

most I/O stuff of the original QD has been changed to handle strings not java.io.files, why?
the standard webpage just needs to know where shit is, the KTORR server will handle actual I/O files and folder, but then ill have real java.io classes to help

bibliography class has been a pain to port, and i think it will work 0% of the time... but regardless no one uses it

Because web browsers cannot write to the user's hard drive without permission, the JS/WASM targets use a 'RAM DiskAll file saves are just written to a `MutableMap<String, ByteArray>` in memory so the web UI can read them for live previews

