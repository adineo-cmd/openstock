# Glossary

| Term             | Meaning |
|------------------|---------|
| **AOSP**         | Android Open Source Project — the Apache-2.0 base all stock OSes are built on. |
| **Clean room**   | Engineering process where implementers work only from a behavioral spec, never from the original's code, to avoid copying protected expression. |
| **Control Center** | Generic term for the quick-settings surface; each stock OS names and shapes it differently (HyperOS Control Center, ColorOS Quick Settings, One UI Quick Panel). |
| **GSI**          | Generic System Image — an AOSP build that boots on any Treble-compliant device; a delivery vehicle for OpenStock. |
| **HyperIsland**  | HyperOS 3.x's pill-shaped live-activity surface around the front camera cutout (spec: SC-HX-002). |
| **Now Bar**      | One UI's lockscreen/pill live-activity surface (spec: SC-OU-002). |
| **Panel (QC)**   | In this repo: the pull-down quick-settings panel as a whole. |
| **Provenance**   | The recorded source of a spec's observations (device, version, method, date). |
| **RRO**          | Runtime Resource Overlay — AOSP mechanism to override framework/app resources at runtime; how the ROM track reskins system UI. |
| **Skin**         | An OpenStock implementation of one stock OS's interaction language (e.g. the `hyperos` skin). |
| **SkinSpec**     | The Kotlin data class holding all tunable parameters of a skin. |
| **Spec**         | A numbered behavioral document in `specs/` (`SC-XX-NNN`). The single source of truth for implementations. |
| **Two-stage shade** | Pull-down gesture that stops at a compact panel first and expands on a second pull (HyperOS-style; spec: SC-HX-001). |
