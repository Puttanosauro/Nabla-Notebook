# 🪐 Qwarkdown Sandbox

A lightweight, WebAssembly-ready port of the official [Quarkdown](https://github.com/iamgio/quarkdown) compiler.

## 🛠 What is this? (The Easy Explanation)
Quarkdown is an incredible Markdown language with superpowers (math macros, variables, dynamic layouts). But there was one problem: **the official compiler was built for desktops and servers**. It relied heavily on Java file systems (`java.io`) and heavy multithreading (`java.util.streams`).

This project is a **Kotlin Multiplatform (KMP)** extraction. We took the official JVM compiler, ripped out the heavy Java dependencies, lobotomized the parallel processing, and made it purely String-based.

**The Goal:** A lightning-fast, client-side web editor that compiles Quarkdown directly in the user's browser using WebAssembly (WASM)—no backend server required.

## 🚀 Current Status
- [x] Ported the core AST, Lexer, and Parser.
- [x] Stripped out `java.io.File` and hard drive dependencies.
- [x] Lobotomized JVM parallel streams for sequential WASM compatibility.
- [ ] Resolving final KMP UUID and Regex dependencies.
- [ ] Hooking the engine up to a Compose Web UI.

## 🧠 Architecture Notes for Future Me
* **The Sledgehammer Strategy:** The core engine is highly interconnected (the AST relies on Context, which relies on Pipelines). The whole `core` folder was ported at once to avoid dependency hell.
* **The `mapParallel` Hack:** True multithreading isn't well-supported in the browser yet. The `mapParallel` utility has been temporarily lobotomized to run standard sequential `.map()` loops. *(Note: We kept the `minItems` parameter in the signature just so the rest of the codebase wouldn't scream at us).*
* **Expect/Actual:** Future server-side implementations can restore true parallelism using Kotlin's `expect/actual` modifiers.