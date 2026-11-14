# OpenStock

**One open Android experience that speaks every stock OS.**

![spec coverage](docs/coverage.svg)

OpenStock is a clean-room, open-source (Apache-2.0) reimplementation of the *signature UX*
of the major Android stock operating systems — Xiaomi **HyperOS**, OPPO/OnePlus **ColorOS**,
**realme UI**, and Samsung **One UI** — built with **100% original code and assets** on top of AOSP.

> ⚠️ **Read [LEGAL.md](LEGAL.md) first.** This project contains **zero** code, assets,
> or decompiled output from any proprietary ROM. Specs are derived from *observable
> behavior* only. See [docs/methodology.md](docs/methodology.md).

## Why

Every year, millions of people love features that are trapped inside one vendor's skin:
HyperOS's glass Control Center, ColorOS's luminous Quick Settings and Smart Sidebar,
One UI's reachability-first layouts and Now Bar. None of it is portable, none of it is open,
and porting decompiled firmware is both illegal and unmaintainable.

OpenStock takes the only path that scales and survives: **spec-first clean-room engineering.**

1. **Observe** — document what a stock OS *does* (layouts, timings, curves, gestures) from
   using a device, never from its code.
2. **Spec** — write implementation-ready specs in [`specs/`](specs/) with a
   [standard template](specs/TEMPLATE.md).
3. **Reimplement** — build it with original Kotlin/AOSP code and original assets.
4. **Ship** — as a launcher/system-UI app first, and as AOSP RRO overlays + a ROM track long-term.

## Fidelity policy: how close can OpenStock get?

**Everything measurable matches exactly; artwork never copies.** Timings, easing curves,
grids, tile sizes, corner radii, motion physics — all captured as numbers in
[`specs/tokens/*.json`](specs/tokens/) and applied verbatim. Icons, wallpapers, fonts,
and sounds are OpenStock originals in the *style* of each skin. That is the legal
ceiling for any open-source clone, and we push right up against it.

The **underlying structure** is a different matter — that part we *do* copy, because it's
Apache-licensed: every vendor skin is a fork of [AOSP SystemUI](docs/structure/systemui-architecture.md).
OpenStock mirrors that architecture component-for-component (`PanelScaffold` ↔ shade/QS
expansion, `TileState` ↔ QS tile model), so everything learned in the app transfers 1:1
to the ROM track.

## What makes OpenStock different

1. **One engine, N skins.** Not a launcher clone per OS — a shared panel/gesture/motion
   engine driven by per-skin tokens. Fix a bug once, every skin benefits.
2. **Tokens are the single source of truth.** [`specs/tokens/*.json`](specs/tokens/) feed
   the Compose app, the web demo, and the generated RRO resources
   (`tools/gen-rro.py`). CI validates schema (`tokens-check.py`), freshness
   (`coverage.py --check`, `skindiff.py --check`, `gen-rro.py --check`) — drift between
   tokens and any consumer fails the build.
   **Prior-art check (2026-11):** the nearest existing thing is Google's
   [Android Automotive OEM design tokens](https://source.android.com/docs/automotive/unbundled_apps/design-tokens)
   — closed-set, vendor-facing, automotive-only. No open project combines a spec corpus,
   a token format, and a multi-skin engine for phone OEM UX; `skindiff`, the coverage
   badge, and the skin-pack format exist nowhere else we could find.
3. **Spec-first with provenance.** Every number in a spec records where it was measured.
   Unmeasured values are marked `~` — honest placeholders, not invented precision.
4. **One live-activity primitive, three skins.** HyperIsland / Fluid Cloud / Now Bar are
   the same idea; OpenStock implements it once (`LiveActivity` model) with per-skin placement.
5. **Legality as a feature.** The decompiler-artifact tripwire in CI and the
   [LEGAL.md](LEGAL.md) policy make this repo forkable, app-store-publishable, and
   takedown-proof — which closed clones are not.

## Repository layout

```
openstock/
├── docs/
│   ├── methodology.md        # the clean-room process — REQUIRED READING for contributors
│   ├── feature-matrix.md     # what each stock OS has, side by side
│   ├── structure/            # AOSP SystemUI architecture (the legally-readable ancestor)
│   ├── skin-pack-format.md   # the community skin-pack format (packs, not forks)
│   ├── diffs/                # skindiff reports — committed, CI-checked for freshness
│   ├── coverage.svg          # spec-coverage badge (generated)
│   └── glossary.md
├── specs/
│   ├── TEMPLATE.md           # every spec uses this shape
│   ├── tokens/               # machine-readable skin tokens — single source of truth
│   ├── hyperos/  coloros/  oneui/
│   └── openstock/            # SC-OS-xxx: OpenStock-original primitives (LiveActivity)
├── tools/
│   ├── tokens-check.py       # token validator (schema + cross-spec consistency)
│   ├── skindiff.py           # behavioral diff between skins — unique to this project
│   ├── coverage.py           # spec-coverage badge generator
│   └── gen-rro.py            # tokens → Android overlay resources (ROM bridge)
├── app/                      # OpenStock Launcher — reference implementation
│   └── ...
└── rom/
    └── overlays/generated/   # per-skin RRO resources, regenerated from tokens
```

*(Generic AOSP scaffolding — repo manifests, hand-written overlay examples — was
removed deliberately; see [rom/README.md](rom/README.md).)*

## The prototype (`app/`)

OpenStock Launcher is a stand-alone Android app (no root, no framework mods) that demonstrates
each skin's most iconic surface — the **quick-settings/control-center panel** — in three flavors:

| Skin        | Panel behavior you can feel in the prototype                            |
|-------------|-------------------------------------------------------------------------|
| `hyperos`   | Glass tiles, two-stage pull-down, notifications inline, spring physics  |
| `coloros`   | Centered circular toggle grid, luminous accent, sidebar edge affordance |
| `oneui`     | Bottom-anchored panel, circular toggles, Now Bar card, big-type headers |

A floating switcher lets you hot-swap skins at runtime; every skin is a pure data + composable
implementation of its spec — no vendor assets anywhere.

Build it:

```bash
cd app
./gradlew :app:assembleDebug   # or open in Android Studio (Arctic-grade AGP, JDK 17+)
adb install app/build/outputs/apk/debug/app-debug.apk
```

## Status

| Track          | State                                                            |
|----------------|------------------------------------------------------------------|
| Specs          | ✅ v0.1 — HyperOS, ColorOS/realme UI, One UI + the SC-OS-001 unified LiveActivity primitive |
| Feature matrix | ✅ v0.1 — 32 features compared across 4 skins                     |
| Launcher proto | ✅ v0.1 — 3 skin panels, skin switcher (source complete)          |
| Toolchain      | ✅ tokens-check · skindiff · coverage · gen-rro (all CI-enforced)  |
| Token→RRO pipe | ✅ generated per-skin overlay resources; binding table next       |
| GMS            | ❌ never — see [LEGAL.md](LEGAL.md)                               |

## Contributing

Start with [docs/methodology.md](docs/methodology.md), then read [CONTRIBUTING.md](CONTRIBUTING.md).
Golden rules:

- **Observe, don't decompile.** Behavior from devices and public documentation only.
- **Spec before code.** A PR that implements a feature must reference its spec ID.
- **Original everything.** Icons, sounds, animations, fonts — all authored in this project.

## License

Apache-2.0 for all code and specs. See [LICENSE](LICENSE).
All product names (HyperOS, ColorOS, realme UI, One UI, Samsung, Xiaomi, OPPO, realme) are
trademarks of their owners, used here solely for identification under nominative fair use.
