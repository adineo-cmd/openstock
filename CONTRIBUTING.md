# Contributing to OpenStock

## The workflow (spec-first, always)

```
observe device → file spec PR → spec merged (behavior only) →
separate implementation PR referencing spec ID → review with spec side-by-side
```

**Never** mix spec authoring and implementation in one PR, and never author a spec while
looking at anything listed in [LEGAL.md](LEGAL.md) §"forbidden sources".

## Writing a spec

1. Copy [`specs/TEMPLATE.md`](specs/TEMPLATE.md) to `specs/<os>/SC-<OS>-<NNN>-<slug>.md`.
2. Fill every section. `### Parameters` is the heart of a spec — timings, curves, radii,
   opacities, counts. A spec without numbers is an opinion.
3. Record your observation method in `## Provenance` (device model, OS version, how measured).
4. Screenshots *you took* of *your own device* are allowed if they show no personal data.
   Vendor marketing renders are not allowed (they may contain copyrighted art).

### ID scheme

| Prefix | Stock OS                    |
|--------|-----------------------------|
| `SC-HX`| HyperOS (Xiaomi/Redmi/POCO) |
| `SC-CO`| ColorOS (OPPO/OnePlus)      |
| `SC-RM`| realme UI                   |
| `SC-OU`| One UI (Samsung)            |
| `SC-OS`| OpenStock original feature  |

## Implementing a spec

- PR title: `impl(SC-HX-001): control center panel — hyperos skin`
- Link the spec in the description. Reviewers verify implementation *against the spec*,
  not against the stock OS.
- New user-visible artwork must be original vector (no tracing vendor bitmaps pixel-for-pixel).
- Animation curves are implemented as data in `SkinSpec` so they're tunable per-skin.

## App code style

- Kotlin, Jetpack Compose, one module. Skin code lives in `skins/<name>/` and may only
  consume the `SkinSpec` API and shared `ui/` primitives — no skin reaching into another
  skin's internals.
- If a behavior is shared across skins (e.g., "panel slides from top"), it belongs in `ui/`,
  parameterized by spec.

## Review gates

1. **Legal gate** — any hint of vendor code/assets: block, cite LEGAL.md.
2. **Spec gate** — numbers match the spec; deviations are called out with reasons.
3. **UX gate** — reviewer must be able to reproduce the behavior from the spec alone.
