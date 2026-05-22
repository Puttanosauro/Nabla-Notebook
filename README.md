# 🪐 Qwarkdown Collaborative Editor

A real-time, WebAssembly-powered collaborative text editor tailored for physics, mathematics, and scientific papers.

> **Note:** This project is currently in active development (Phase 1: Local Sandbox).

## 🛠 Project Vision
Modern word processors struggle with complex STEM notation, and professional typesetting tools can feel cumbersome for everyday drafting. This project serves as a bridge between the two.

We provide an accessible, Google Docs-style collaborative environment for the [Quarkdown (.qd) language](https://github.com/iamgio/quarkdown). By offloading heavy computational rendering to the client via **WebAssembly (WASM)**, we deliver a latency-free editing experience while keeping the backend lightweight and self-hostable. Users can fluidly switch between a real-time, intuitive preview and the raw `.qd` source for full LaTeX-level control.

## 🏗 Architecture
* **The Core:** Leverages the [Quarkdown](https://github.com/iamgio/quarkdown) ecosystem, ported to Kotlin Multiplatform (KMP/WASM) to enable high-performance client-side rendering.
* **Frontend:** A dual-mode interface offering real-time visual rendering alongside raw `.qd` source control.
* **Backend:** Kotlin (Ktor) acting as a lightweight sync-router, document validator, and state manager.
* **Sync & Persistence:** CRDT-based synchronization for real-time collaboration, paired with a hybrid storage model (Redis for session caching, append-only logs for history, and SQL for final snapshots).

## 🛠 Development Roadmap
We are currently in **Phase 1: The Local Sandbox**.
### Completed
* Initiated port of `quarkdown-core` to Kotlin Multiplatform (KMP).
* removed the JVM-specific dependencies from the KMP version:
  * Refactored `ast` package to remove JVM-specific dependencies (`java.io`, thread pools).
  * Refactored `bibliography` package to remove JVM-specific dependencies (`java.io`).

### In Progress / Next Steps
To finish phase 1 we need to:
* finish the port of of `quarkdown-core` to Kotlin Multiplatform (KMP), missing:
  * [context](app/shared/src/commonMain/kotlin/org/example/project/qdcore/context)
  * [document](app/shared/src/commonMain/kotlin/org/example/project/qdcore/document)
  * [flavor](app/shared/src/commonMain/kotlin/org/example/project/qdcore/flavor)
  * [function](app/shared/src/commonMain/kotlin/org/example/project/qdcore/function)
  * [graph](app/shared/src/commonMain/kotlin/org/example/project/qdcore/graph)
  * [lexer](app/shared/src/commonMain/kotlin/org/example/project/qdcore/lexer)
  * [localization](app/shared/src/commonMain/kotlin/org/example/project/qdcore/localization)
  * [log](app/shared/src/commonMain/kotlin/org/example/project/qdcore/log)
  * [media](app/shared/src/commonMain/kotlin/org/example/project/qdcore/media)
  * [misc](app/shared/src/commonMain/kotlin/org/example/project/qdcore/misc)
  * [parser](app/shared/src/commonMain/kotlin/org/example/project/qdcore/parser)
  * [permissions](app/shared/src/commonMain/kotlin/org/example/project/qdcore/permissions)
  * [pipeline](app/shared/src/commonMain/kotlin/org/example/project/qdcore/pipeline)
  * [property](app/shared/src/commonMain/kotlin/org/example/project/qdcore/property)
  * [rendering](app/shared/src/commonMain/kotlin/org/example/project/qdcore/rendering)
  * [template](app/shared/src/commonMain/kotlin/org/example/project/qdcore/template)
  * [util](app/shared/src/commonMain/kotlin/org/example/project/qdcore/util)
  * [visitor](app/shared/src/commonMain/kotlin/org/example/project/qdcore/visitor)
  
## 🧩 Technical Note:
WASM and the JVM don't exactly love one another.
Because of this, many features available in the original QD (qwardwon) code simply aren't supported in the WebAssembly port

To maintain a full, functional port, I’ve had to make some compromises
  * **"Lobotomized" Classes:** Many classes in the WASM version are stripped-down versions of their JVM counterparts and might behave slightly differently than what standard QD does.
  *  **Interface Abstraction:** This is achieved using Kotlin interfaces in `commonMain`.

What this approach achieves is a mostly seamless experience while editing and ensuring at the same time that the server HTML/PDF renders are accurate
### Feature Parity & Limitations
| Feature                    | Browser/WASM Behavior | Server-Side Implementation | developer comment                                                                                               |
|:---------------------------|:----------------------|:---------------------------|:----------------------------------------------------------------------------------------------------------------|
| **Multi-thread Rendering** | Stripped/Disabled     | still missing              | in a web page this is mostly useless and the difference is barely notable                                       |
| **CSL Citation Rendering** | Placeholder/Stubbed   | still missing              | the numbering and citation wont fully work as intended, tho the placeholder are designed to be atleast readable |

> **Note:** *We use abstraction via `commonMain` interfaces to ensure that features which cannot run in the browser can still be fully implemented on the server-side compiler for accurate final PDF/HTML rendering*