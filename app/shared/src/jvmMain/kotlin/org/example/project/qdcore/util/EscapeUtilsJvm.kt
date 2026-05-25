package org.example.project.qdcore.util

import org.apache.commons.text.StringEscapeUtils
import java.net.URLEncoder

actual object Escape {
    actual object Html : EscapeTarget, UnescapeTarget {
        override fun escape(input: String): String = StringEscapeUtils.escapeHtml4(input)
        override fun unescape(input: String): String = StringEscapeUtils.unescapeHtml4(input)
    }

    actual object JavaScript : EscapeTarget, UnescapeTarget {
        override fun escape(input: String): String = StringEscapeUtils.escapeEcmaScript(input)
        override fun unescape(input: String): String = StringEscapeUtils.unescapeEcmaScript(input)
    }

    actual object Json : EscapeTarget, UnescapeTarget {
        override fun escape(input: String): String = StringEscapeUtils.escapeJson(input)
        override fun unescape(input: String): String = StringEscapeUtils.unescapeJson(input)
    }

    actual object Url : EscapeTarget {
        override fun escape(input: String): String =
            input.split("/").joinToString("/") {
                URLEncoder.encode(it, Charsets.UTF_8.name()).replace("+", "%20")
            }
    }
}