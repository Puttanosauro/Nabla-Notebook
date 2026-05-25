package org.example.project.qdcore.util

// honestly this class... i dont expect this to work
// this has been implemented as best as i could, but i fear it wont be enough
// may god have mercy on me

interface EscapeTarget {
    fun escape(input: String): String
}

interface UnescapeTarget {
    fun unescape(input: String): String
}

expect object Escape {
    object Html : EscapeTarget, UnescapeTarget {
        override fun escape(input: String): String
        override fun unescape(input: String): String
    }

    object JavaScript : EscapeTarget, UnescapeTarget {
        override fun escape(input: String): String
        override fun unescape(input: String): String
    }

    object Json : EscapeTarget, UnescapeTarget {
        override fun escape(input: String): String
        override fun unescape(input: String): String
    }

    object Url : EscapeTarget {
        override fun escape(input: String): String
    }
}