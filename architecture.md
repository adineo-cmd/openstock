# System Architecture

## Components
- **Token layer** (`specs/tokens/*.json`) — canonical skin data: color, shape, layout,
  panel behavior, motion. Validated by `tokens-check.py`.
- **Spec layer** (`specs/**/*.md`) — behavior contracts (`SC-XX-NNN`) with provenance;
  includes OpenStock-original primitives (`specs/openstock/`, e.g. SC-OS-001 LiveActivity).
- **Toolchain** (`tools/`) — `tokens-check` (schema+consistency), `skindiff` (behavioral
  diff), `coverage` (badge/stats), `gen-rro` (tokens → Android overlay resources).
- **App** (`app/`) — Kotlin/Compose reference implementation: shared `PullPanel` engine +
  `PanelState`, per-skin `SkinSpec` + composables, `HomeScreen`, `SkinSwitcher`.
- **ROM bridge** (`rom/overlays/generated/`) — generated RRO resources per skin; binding
  to a pinned AOSP SystemUI baseline is the next phase.
- **Docs** — methodology (clean-room), feature matrix, SystemUI structure map, skin-pack
  format, committed skindiff reports.

## Data Flow
`specs/tokens/*.json` → `tokens-check.py` (gate) → three consumers:
(1) Compose `SkinSpec` values (hand-synced until codegen exists),
(2) web demo `TOKENS` (embedded copy),
(3) `gen-rro.py` → `rom/overlays/generated/*`.
`coverage.py` reads specs + app + matrix → `docs/coverage.svg`.
`skindiff.py` reads tokens → `docs/diffs/*.md`.
CI re-verifies freshness of every generated artifact.

## Key Decisions
- Clean-room over decompilation: legality + maintainability (see LEGAL.md).
- Skins as data over skin forks: one engine, mechanical diffs, community packs.
- AOSP SystemUI mirrored deliberately (CentralSurfaces/shade/QS/scrim decomposition) so
  app learnings transfer 1:1 to the ROM track.
- App-first distribution (unrooted phones) before RRO before full ROM.

## Diagrams
- Pipeline + state machines live inside the specs (mermaid): `specs/hyperos/SC-HX-001`,
  `specs/openstock/SC-OS-001`; token→RRO flow in `rom/README.md`.
