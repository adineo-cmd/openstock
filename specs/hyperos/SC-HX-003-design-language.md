# SC-HX-003: HyperOS skin design language

| Field          | Value |
|----------------|-------|
| **ID**         | SC-HX-003 |
| **Source OS**  | HyperOS 3.x (observed behavior) |
| **Status**     | stable |
| **Priority**   | P1 |
| **OpenStock skin** | `hyperos` |
| **Implements** | matrix rows #14, #21, #23 |

## 1. Summary

The visual/motion character of the `hyperos` skin: layered translucency ("glass"),
generous rounding, spring-based motion, and a soft neutral palette with a single accent.

## 2. Tokens

| Token                | Value (OpenStock original)              |
|----------------------|-----------------------------------------|
| `corner.panel`       | 28 dp                                   |
| `corner.tile`        | 16 dp                                   |
| `corner.card`        | 20 dp                                   |
| `surface.glass`      | white @ 72% (light) / #101418 @ 68% (dark) + 24 dp blur |
| `accent`             | OpenStock cyan (`#0AA2C4` light, `#3FD0E8` dark) — chosen by us |
| `text.primary`       | neutral-90 / neutral-95                 |
| `type.display`       | 28 sp / w6 — clock & headers            |
| `type.body`          | 15 sp / w4                              |
| `type.caption`       | 12 sp / w4                              |
| `motion.spring.default` | damping 0.82, stiffness 380          |
| `motion.spring.bouncy`  | damping 0.72, stiffness 300          |
| `motion.duration.fast`  | 150 ms (state flips, haptic ticks)   |

## 3. Motion character rules

1. **Everything springs.** No linear or pure-ease transitions for container movement.
2. **Overshoot budget**: containers may overshoot ≤ 6% of travel; tiles/icons ≤ 2%.
3. **Interruption is retargeting, not restart**: a spring mid-flight that gets a new
   target continues from current position/velocity.
4. Haptics: tick (8 ms) on toggle, medium (16 ms) on commit (drop, reorder settle).

## 4. Iconography rules (original artwork)

- Squircle superellipse base, radius ≈ 22.5% of icon size.
- Icons are **2.5D-flavored**: single soft top-light gradient (≤ 8% luminance shift),
  subtle inner shadow at bottom edge. No multi-color faux-3D.
- Grid: 5 columns default; icon 52 dp, label 11 sp below, 18 dp row gap.

## 5. Verification

- [ ] All container motion uses spring spec tokens; zero `LinearEasing` in skin code.
- [ ] Light/dark palettes both pass 4.5:1 text contrast on glass surfaces.
- [ ] Icon gradients stay within the ≤ 8% luminance-shift rule (checked in review).

## 6. Provenance

- Observed on: public HyperOS 3.x reviews/design coverage, 2026-11.
- Method: token synthesis — vendor palette is *not* copied; OpenStock picks its own
  hex values that reproduce the *role structure* (glass/accent/neutral).
