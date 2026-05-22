# 🪐 Qwarkdown Collaborative Editor

A real-time, WebAssembly-powered collaborative text editor tailored for physics, mathematics, and scientific papers.

## 🛠 Project Vision
Standard word processors struggle with complex STEM notation. This project solves that by utilizing **Qwarkdown** (`.qd`), a custom markup language with superpowers. By offloading the heavy computational rendering to the user's browser via WebAssembly (WASM), we ensure a lightning-fast, latency-free editing experience, while the central server acts as a lightweight router for document state synchronization.

## 🏗 System Architecture
* **Frontend (WASM):** Kotlin Multiplatform client handling local rendering and a two-tier UI (real-time Markdown + debounced math formula rendering).
* **Backend (Ktor):** Kotlin server managing WebSockets, validating CRDT updates, and broadcasting changes.
* **Synchronization:** CRDTs (Conflict-free Replicated Data Types) for mathematically safe, simultaneous multi-user editing.
* **Storage Layer:** Redis (active document caching), Git-like append-only DB (version history and deltas), and Relational SQL (snapshots and user data).

## 🚀 Current Status: Phase 1 (The Local Sandbox)
We are actively building the single-player Kotlin WASM client.
**Recent Milestone:** Performed a surgical "sledgehammer" port of the official `quarkdown-core` JVM compiler into pure Kotlin Multiplatform. We stripped out `java.io` file system dependencies and lobotomized JVM parallel streams to ensure the engine compiles natively for the web browser.