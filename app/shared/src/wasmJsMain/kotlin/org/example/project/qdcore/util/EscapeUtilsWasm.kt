package org.example.project.qdcore.util

actual object Escape {
    actual object Html : EscapeTarget, UnescapeTarget {
        override fun escape(input: String): String {
            return input.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;")
        }

        override fun unescape(input: String): String {
            return input.replace("&lt;", "<")
                .replace("&gt;", ">")
                .replace("&quot;", "\"")
                .replace("&#39;", "'")
                .replace("&amp;", "&")
        }
    }

    actual object JavaScript : EscapeTarget, UnescapeTarget {
        override fun escape(input: String): String {
            return input.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("'", "\\'")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t")
        }

        override fun unescape(input: String): String {
            return input.replace("\\\"", "\"")
                .replace("\\'", "'")
                .replace("\\n", "\n")
                .replace("\\r", "\r")
                .replace("\\t", "\t")
                .replace("\\\\", "\\")
        }
    }

    actual object Json : EscapeTarget, UnescapeTarget {
        override fun escape(input: String): String {
            // JSON strings don't allow unescaped quotes or slashes
            return input.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t")
        }

        override fun unescape(input: String): String {
            return input.replace("\\\"", "\"")
                .replace("\\n", "\n")
                .replace("\\r", "\r")
                .replace("\\t", "\t")
                .replace("\\\\", "\\")
        }
    }

    actual object Url : EscapeTarget {
        override fun escape(input: String): String {
            // Pure Kotlin URL Percent Encoder
            val allowed = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_.~"
            return input.split("/").joinToString("/") { segment ->
                val encodedSegment = StringBuilder()
                for (char in segment) {
                    if (char in allowed) {
                        encodedSegment.append(char)
                    } else {
                        // Convert to UTF-8 bytes and percent-encode
                        val bytes = char.toString().encodeToByteArray()
                        for (byte in bytes) {
                            encodedSegment.append('%')
                            val hex = byte.toUByte().toString(16).uppercase()
                            if (hex.length == 1) encodedSegment.append('0')
                            encodedSegment.append(hex)
                        }
                    }
                }
                encodedSegment.toString()
            }
        }
    }
}