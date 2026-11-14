# Methodology: Spec-First Clean-Room Reverse Engineering

*Required reading before contributing a spec or an implementation.*

## 1. Principle

OpenStock reverse engineers **behavior**, never **code**. Copyright law protects the
expression of an idea (source code, compiled binaries, artwork), not the idea itself
(features, interaction flows, visual organization). Our process is designed so that:

- every contributor can prove their knowledge came from **allowed sources**;
- no artifact in this repo is **substantially similar** to any vendor's expression;
- a third party can rebuild each feature from our specs alone.

## 2. The pipeline

```
┌──────────┐   ┌──────────┐   ┌──────────────┐   ┌───────────────┐
│ OBSERVE  │ → │ SPECIFY  │ → │ IMPLEMENT    │ → │ VERIFY        │
│ (device  │   │ (numbers,│   │ (original    │   │ (spec-only A/B,│
│ as user) │   │ behavior)│   │ code/assets) │   │ side-by-side) │
└──────────┘   └──────────┘   └──────────────┘   └───────────────┘
     ↑                                   │
     └── iteration loop ───────────────┘
```

### 2.1 Observe

Work on **a device you own** with the stock OS installed. Allowed instruments:

| Instrument                | What it measures                                   |
|---------------------------|----------------------------------------------------|
| Slow-motion screen capture| Animation phases, durations, easing shapes         |
| Stopwatch / frame counter | Transition durations (at 60/120 fps frame counts)  |
| On-screen ruler / overlay | Layout paddings, tile sizes, corner radii (in dp)  |
| Color picker              | Palette, opacity layers                            |
| Haptics notes             | Vibration pattern on key interactions              |

Record **what happens**, in user-terms: *"pulling down on the home screen stops at a
half-open panel showing 6 tiles; a second pull from there expands to the full panel"*.

### 2.2 Specify

Translate observations into a numbered spec using [specs/TEMPLATE.md](../specs/TEMPLATE.md).
A good spec contains:

- **Behavioral states** and the transitions between them (a state diagram in prose or mermaid).
- **Parameters** with real numbers and units (`240 dp`, `spring(damping 0.78, stiffness 380)`,
  `180 ms ease-out`). If you couldn't measure precisely, give a range and mark it `~`.
- **Edge cases**: one-handed mode, landscape, dark mode, RTL, accessibility scaling.
- **Provenance**: device model, OS version, observation date, method.

The spec is the **only** artifact an implementer needs. If an implementer needs to look at
the stock device to code a feature, the spec has failed — fix the spec instead.

### 2.3 Implement

Implementations reference spec IDs (`SC-HX-001`) and consume behavior as **data**
(`SkinSpec` data classes: colors, curves, sizes) so skins stay tunable and comparable.
All artwork is original vector, authored in-repo. All sounds are synthesized or licensed.

### 2.4 Verify

Acceptance for an implementation PR:

1. A reviewer implements-side-by-side against the **spec only** and confirms behavior parity.
2. Optional: record side-by-side videos (spec-built vs. stock device) for the spec's
   `## Verification` section. Videos of stock devices must show no personal data.

## 3. Why not just decompile? (FAQ)

**Q: Decompiling would be faster.**
A: It's copyright infringement the moment we redistribute; it makes the repo legally
poisoned (any derived work — forks, distros, app stores — inherits the taint); and decompiled
output is unreadable spaghetti that nobody can maintain across Android versions. Specs stay
readable for a decade.

**Q: Is "cloning the UI" not also infringement?**
A: Copying *expression* is; reimplementing *functional behavior* described in a spec is the
established clean-room practice. Our assets, code, and names are all our own. What remains
shared is the underlying idea — which the law does not reserve.

**Q: Can I paste a screenshot of my device into the spec?**
A: Yes, if it's your device and contains no personal data — screenshots document observable
state, not expression we copy. Do not paste vendor marketing renders.

**Q: What about patents?**
A: Some UX mechanics are patented. Specs with a known patent risk get a `⚠️ patent-review`
tag in `## Provenance`; implementations of tagged specs need maintainer sign-off. In
practice the panels and gestures documented so far are unpatented, widely-duplicated
interaction patterns.

## 4. Contamination protocol

If forbidden material ever enters the repo (a paste, a traced asset, a dump):

1. Open a `[LEGAL]` issue immediately; do not link the content.
2. Maintainers remove it and purge it from history (`git filter-repo`) if needed.
3. Contributors involved re-read [LEGAL.md](../LEGAL.md) before continuing.

History hygiene beats a quiet takedown later.
