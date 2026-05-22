# 🪐 Qwarkdown Collaborative Editor

A real-time, WebAssembly-powered collaborative text editor tailored for physics, mathematics, and scientific papers.
> **Note:** This project is currently in active development (Phase 1: Local Sandbox).

## 🛠 Project Vision
Standard word processors (like Google Docs) struggle with the complexities of STEM notation. **Qwarkdown (.qd)** is a custom markup language designed to bridge this gap. By offloading heavy computational rendering to the client via **WebAssembly (WASM)**, we provide a latency-free editing experience while keeping our backend as a lightweight router for synchronization.

## 🏗 Architecture
* **Frontend:** Kotlin Multiplatform (WASM) handling local rendering with a two-tier system: real-time markdown preview and debounced math formula rendering.
* **Backend:** Kotlin (Ktor) managing WebSocket connections and CRDT validation.
* **Sync:** CRDT-based synchronization (e.g., Yjs) for mathematically safe, simultaneous multi-user editing.
* **Persistence:** A hybrid approach using Redis for active session caching, an append-only log for version history, and SQL for snapshots.

## 🛠 Development Roadmap
We are currently in **Phase 1: The Local Sandbox**.
### Completed
* Initiated port of `quarkdown-core` to Kotlin Multiplatform (KMP).
* Refactored `ast` package to remove JVM-specific dependencies (`java.io`, thread pools).

### In Progress / Next Steps
//todo
## 🧩 Technical Note:
To ensure the engine runs natively in the browser, we are porting the `quarkdown-core` JVM compiler to KMP.
Since WASM and the JVM don't love each-other and have different runtime constraints, we use **Kotlin Interfaces** in `commonMain` to abstract away unsupported features.
This allows us to maintain a shared codebase while ensuring the browser-side "lobotomized" engine remains performant and ready for accurate HTML/PDF final rendering. 

### Feature Parity & Limitations
| Feature                    | Browser/WASM Behavior | Server-Side Implementation | developer comment                                                                                               |
|:---------------------------|:----------------------|:---------------------------|:----------------------------------------------------------------------------------------------------------------|
| **Multi-thread Rendering** | Stripped/Disabled     | still missing              | in a web page this is mostly useless and the difference is barely notable                                       |
| **CSL Citation Rendering** | Placeholder/Stubbed   | still missing              | the numbering and citation wont fully work as intended, tho the placeholder are designed to be atleast readable |

> **Note:** *We use abstraction via `commonMain` interfaces to ensure that features which cannot run in the browser can still be fully implemented on the server-side compiler for accurate final PDF/HTML rendering*