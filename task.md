# Tasks

## Active
- Push repo to GitHub `adineo-cmd` — in progress (this session).
- Comment strip per owner's global rule — done; Kotlin merge damage repaired; pipeline green.

## Backlog
- Device measurements for 51 `~` markers across specs — needs HyperOS/ColorOS/One UI
  devices; slow-mo capture + on-screen rulers; file measurement PRs against specs. (P0)
- Compile the app: install Android SDK, `gradle wrapper --gradle-version 8.9`, fix any
  AGP/Compose version drift, then iterate on feel. (P0)
- SkinSpec codegen from tokens (`tools/gen-skinspec.py`) — removes the last hand-synced
  consumer. (P1)
- Skin-pack loader in the app (install packs per `docs/skin-pack-format.md`). (P1)
- LiveActivity primitive implementation (SC-OS-001) in the app. (P1)
- RRO binding table against a pinned AOSP 16 SystemUI baseline + generated `Android.bp`
  per skin. (P1)
- Real device trees / GSI build after binding table proves out. (P2)
- More skins as community packs (FunTouch, Nothing OS) once the pack loader exists. (P2)

## Blocked
- App compilation — no Android SDK in this environment; needs the owner's machine or CI.
- Spec measurements — needs physical devices.
