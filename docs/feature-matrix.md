# Cross-OEM Feature Matrix (v0.1)

Comparison of the four major Android stock-OS families, as observed from public releases
(current as of **November 2026**). This matrix drives the spec backlog: a ✅ in
"OpenStock" means a spec exists *and* the prototype implements the panel behavior.

Legend: ✅ has it (natively) · 🟡 partial / optional · ❌ no · 🔗 via companion app/service

| # | Surface / Feature                | HyperOS 3.1 (Xiaomi) | ColorOS 17 (OPPO/OnePlus) | realme UI 8 | One UI 8.5 (Samsung) | AOSP 16 | OpenStock |
|---|----------------------------------|----------------------|---------------------------|-------------|----------------------|---------|-----------|
| 1  | Quick-settings panel style      | Glass tiles, 2-stage shade | Circular toggles, centered | Circular toggles (ColorOS-based) | Circular toggles, resizable grid | Pill QS, single stage | ✅ all 3 skins |
| 2  | Two-stage shade (compact→full)  | ✅                   | ❌ (single stage)         | ❌          | 🟡 (swipe twice)     | 🟡 (QS twice-pull) | ✅ hyperos |
| 3  | Notification shade separation  | ✅ separate from CC  | 🟡 combined panel         | 🟡 combined | ✅ separate pages     | ✅ combined | 🟡 hyperos only |
| 4  | Brightness control placement   | Inline slider in CC  | Inline, full-width        | Inline      | Inline + resizable   | Inline   | ✅ all     |
| 5  | Live-activity pill (island)    | ✅ HyperIsland       | 🟡 (Fluid Cloud style)    | 🟡          | ✅ Now Bar / Now Brief | 🟡 (rich Ongoing) | 🟡 spec'd |
| 6  | Lockscreen live activities     | ✅                   | ✅ Flux cards             | ✅          | ✅ Now Bar           | 🟡       | 🟡 spec'd  |
| 7  | AOD customization              | ✅ deep (themes)     | ✅ (luminous styles)      | ✅          | ✅ (GRX/AOD themes)  | 🟡 basic | ❌ backlog |
| 8  | Always-listening bar           | ✅                   | 🟡                        | 🟡          | 🟡                   | ❌       | ❌ backlog |
| 9  | Recents: layout                | Stacked cards (3.1)  | Horizontal cards          | Horizontal  | Horizontal cards + split handles | Horizontal | 🟡 backlog |
| 10 | Split-screen gesture           | ✅ (long-press app)  | ✅ (Smart Sidebar drag)   | ✅          | ✅ (edge handle)     | ✅       | ❌ backlog |
| 11 | Floating windows / freeform    | ✅ (freeform)        | ✅ FlexDrop-style         | ✅          | 🟡 (popup via DEX)   | 🟡 hidden | ❌ backlog |
| 12 | Smart sidebar / edge panel     | ❌                   | ✅ Smart Sidebar          | ✅          | ✅ Edge Panels       | ❌       | 🟡 coloros+oneui |
| 13 | Home: app drawer               | 🟡 optional          | ✅                        | ✅          | ✅                   | ✅       | ✅ proto   |
| 14 | Home: icon style               | 2.5D rounded squircle| Luminous glass squircle   | Squircle    | Squircle, themed icons | Adaptive | ✅ code-gen |
| 15 | Home: widget shelf             | ✅ (-1 screen)       | 🟡                        | 🟡          | 🟡 (Google Discover) | ❌       | ❌ backlog |
| 16 | Home: grid resize              | ✅                   | ✅                        | ✅          | ✅                   | 🟡       | 🟡 backlog |
| 17 | Folder styles                  | ✅ animated folders  | ✅ large folders          | ✅          | ✅ stacked/tabbed    | 🟡       | ❌ backlog |
| 18 | Lockscreen: shortcuts          | ✅ editable          | ✅                        | ✅          | ✅ editable          | ✅       | 🟡 backlog |
| 19 | Lockscreen: clock styles       | ✅ dozens            | ✅                        | ✅          | ✅ dozens            | 🟡 few   | ❌ backlog |
| 20 | Always-on typing (video AOD)   | ✅                   | ✅                        | ✅          | ✅                   | ❌       | ❌ backlog |
| 21 | Dark mode scheduling           | ✅ per-app force     | ✅                        | ✅          | ✅                   | ✅       | 🟡 backlog |
| 22 | Icon pack support              | ✅ Themes store      | 🟡 limited                | 🟡 limited  | ✅ (Good Lock)       | 🟡       | 🟡 via specs |
| 23 | Sound & haptics engine         | ✅ rich              | ✅ (O-Haptics style)      | ✅          | ✅ (regional)        | 🟡 basic | 🟡 haptics spec'd |
| 24 | Screenshot: scrolling          | ✅                   | ✅                        | ✅          | ✅ (Scroll capture)  | ✅       | ❌ backlog |
| 25 | Screenshot: partial region     | ✅                   | ✅                        | ✅          | ✅                   | 🟡       | ❌ backlog |
| 26 | One-handed mode                | ✅                   | ✅                        | ✅          | 🟡 (via One hand+)   | ❌       | ❌ backlog |
| 27 | Gaming mode / perf profile     | ✅ Game Turbo        | ✅ (game focus)           | ✅          | ✅ Game Booster      | ❌       | ❌ backlog |
| 28 | Cross-device continuity        | ✅ (Mi ecosystem)    | ✅ (O-Connect style)      | 🟡          | ✅ (Multi Control, Link) | ❌  | ❌ out of scope v1 |
| 29 | Privacy dashboard extras       | 🟡                   | 🟡 (App Lock deep)        | 🟡          | ✅ (Auto Blocker)    | ✅ base  | ❌ backlog |
| 30 | Quick Panel customization      | 🟡 (style switch)    | ✅ reorder                | ✅ reorder  | ✅ resize/reposition | 🟡 reorder | 🟡 spec'd |
| 31 | Writing/marker tools           | ✅                   | ✅ (Smart Touch style)    | 🟡          | 🔗 (S Pen only)      | ❌       | ❌ backlog |
| 32 | AI assistant integration       | ✅ (Super XiaoAI)    | ✅                        | ✅          | ✅ (Galaxy AI)       | 🔗 (Gemini) | ❌ out of scope v1 |

## Read-through: what's actually worth speccing first

1. **The quick-settings panel is the single most brand-defining surface** — every OEM's
   is instantly recognizable, it's touched 30+ times a day, and it's implementable in a
   launcher app without framework changes. Hence SC-001 for every skin.
2. **Live-activity pills** (HyperIsland / Fluid Cloud / Now Bar) converge on the same
   *idea* — a system-hosted, expandable ongoing-activity surface. One shared OpenStock
   primitive with three skins is high leverage.
3. **Everything AI / cross-device / ecosystem** is out of scope for v1: it depends on
   vendor clouds we can't and shouldn't replicate.

## Version coverage note

Specs pin behavior to the version listed in their Provenance. When an OEM ships a new
major version, specs get a `## Version delta` section appended rather than rewritten,
so history stays traceable.
