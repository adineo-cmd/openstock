# Product Requirements Document

## Overview
- Project: OpenStock
- Objective: one open-source (Apache-2.0), clean-room engine that reimplements the
  signature UX of OEM Android skins (HyperOS, ColorOS/realme UI, One UI) from measured
  behavior — never from vendor code or assets.
- Scope: in-scope — quick-settings panels, live-activity surfaces, design-language
  tokens, launcher prototype, token→RRO pipeline, spec corpus + toolchain.
  Out-of-scope — vendor AI/cloud services, GMS, cross-device ecosystems, full device trees.

## Requirements
1. Behavioral parity: every implemented surface cites a spec (`SC-XX-NNN`) whose numbers
   come from observation or are marked `~` (unmeasured).
2. Skins as data: all skin differences live in `specs/tokens/*.json`; one shared engine
   (app, web demo, RRO generator) consumes tokens — no per-skin forks.
3. Legal cleanliness: zero vendor code/assets/trademarks; CI tripwire enforces it.
4. Toolchain uniqueness maintained: `skindiff`, `coverage`, `gen-rro`, `tokens-check`
   stay green and fresh in CI.
5. Fidelity: geometry/motion match token values exactly (px = dp × scale on 360 dp width).

## Constraints
- No Android SDK in the working environment (app source-complete, uncompiled).
- Vendor specs currently rest on public materials; 51 values marked `~` await device
  measurement.
- Clean-room rule limits visual fidelity to behavior + original artwork.

## Acceptance Criteria
- All four tools pass (`PIPELINE GREEN`) on every commit; CI green.
- Every token edit regenerates badge/diffs/RRO outputs in the same commit.
- A reviewer can verify any implementation against its spec alone.
