# The Underlying Structure: AOSP SystemUI and how every skin extends it

*Sources: `aosp-mirror/platform_frameworks_base` (Apache-2.0 — freely readable and
referenced), plus publicly observable vendor behavior. No proprietary code was read
to write this document.*

## 1. The one fact that unlocks everything

**HyperOS, ColorOS/realme UI, and One UI are all forks of AOSP SystemUI.** They are not
separate operating systems. Each vendor copies AOSP's `packages/SystemUI`, renames and
reskins it, adds their framework hooks, and ships the result as `com.android.systemui`
(or a vendor package replacing it). This means:

- the *architecture* of every skin is public knowledge — it's Apache-licensed;
- the *differences* between skins are behavior + resources — exactly what our specs capture;
- OpenStock's ROM track can mirror the real component structure legally, because the
  ancestor's license (Apache-2.0) explicitly permits derivation with attribution.

## 2. AOSP SystemUI component map (verified against current source)

Top-level structure of `packages/SystemUI/src/com/android/systemui/`:

| AOSP component          | Path                        | What it owns                                   |
|-------------------------|-----------------------------|------------------------------------------------|
| Entry point / DI        | `SystemUIApplication`, `SystemUIService`, `dagger/` | Boot, dependency graph, `CoreStartable`s |
| **Central surfaces**    | `statusbar/phone/CentralSurfaces(Impl)` | The main phone SystemUI orchestrator (status bar + shade + keyguard glue) |
| Status bar window       | `statusbar/phone/PhoneStatusBarView`, `StatusIconContainer`, `DarkIconDispatcher` | The bar itself, icon layout, tinting |
| Shade / expansion       | `shade/`, `ShadeTouchableRegionManager` | Pull-down shade state machine, touch regions, expansion fraction |
| Quick settings          | `qs/`                       | Tile model, tile views, QS panel, footer       |
| Notifications           | `notifications/`            | Notification stack, heads-up, grouping         |
| Scrim                   | `scrim/`, `ScrimController` | Behind-shade/lockscreen dimming states         |
| Keyguard                | `keyguard/`, `bouncer/`     | Lock screen, unlock, biometric bouncer         |
| Brightness              | `brightness/`               | Brightness slider controller                   |
| Volume                  | `volume/`                   | Volume panel                                   |
| Recents / WMShell hooks | `recents/`, `wmshell/`      | Overview, split-screen (Shell-side)            |
| Global actions          | `globalactions/`            | Power menu                                     |
| Screenshot / record     | `screenshot/`, `screenrecord/` | Capture surfaces                            |
| Controls / wallet       | `controls/`, `wallet/`      | Device controls, wallet Quick Access           |
| Themes / colors         | `theme/`, `colorextraction/` | Dynamic color, themed icons                   |

**Why this matters to OpenStock:** our prototype already mirrors this decomposition —
`PanelScaffold`+`PanelState` ≈ the shade/QS expansion surface, `TileState` ≈ the QS tile
model, `SkinSpec` ≈ the resource/overlay layer, `HomeScreen` ≈ launcher (separate package
in real Android, `Launcher3`). The ROM track's SystemUI module will keep this 1:1 mapping
so behavior learned in the app transfers to the ROM.

## 3. How each vendor extends the ancestor (observable structure)

| Skin             | Known structural pattern (public/observable)                                                                 |
|------------------|--------------------------------------------------------------------------------------------------------------|
| **HyperOS**      | Replaces SystemUI with Xiaomi's fork; adds MIUI framework services (power keeper, perf hooks); Control Center is a rewritten `qs/`+`shade/` pair with its own two-stage state machine; Themes app swaps resources via their theme engine. |
| **ColorOS/realme UI** | OPlus fork of SystemUI; single-stage QS panel; Smart Sidebar lives in a separate overlay window; shares the OPlusOS base between OPPO/OnePlus/realme with resource-level differentiation. |
| **One UI**       | Samsung fork; keeps AOSP notification/shade split, adds SEAndroid/ Knox plumbing, Edge Panels as plugin windows, Good Lock as resource/behavior plugins. |

These patterns are visible from package listings, settings screens, and public developer
documentation — none of this required reading vendor code. When a vendor documents
nothing, we spec the *observable behavior* (our `specs/` files) and leave internals alone.

## 4. Extension mechanisms OpenStock uses (all public API)

1. **Runtime Resource Overlays (RRO)** — override `SystemUI` resources at runtime;
   how the ROM track reskins panels without forking Java/Kotlin (`rom/overlays/rro/`).
2. **SystemUI plugins** — AOSP supports plugins (`PluginInflateContainer`, `plugins/`);
   OpenStock panels can ship as a plugin in later phases.
3. **AOSP fork module** — the eventual `packages/openstock/SystemUI` (manifest in
   `rom/manifests/`): our own implementation of the same component map, Apache-licensed,
   with skin selection at build time.

## 5. Reading list (all Apache-2.0)

- `frameworks/base/packages/SystemUI/` — the app we're mirroring
- `frameworks/base/libs/WindowManager/Shell/` — split-screen/freeform (wmshell)
- `frameworks/base/core/java/com/android/internal/` — internal APIs skins hook

Rule of thumb: **if it's in AOSP, read it, quote it, fork it (with attribution).
If it's in a vendor APK, observe it and spec it — never decompile it.**
