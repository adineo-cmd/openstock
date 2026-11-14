# SC-CO-003: ColorOS skin design language ("luminous glass")

| Field          | Value |
|----------------|-------|
| **ID**         | SC-CO-003 |
| **Source OS**  | ColorOS 16/17 (observed behavior) |
| **Status**     | stable |
| **Priority**   | P1 |
| **OpenStock skin** | `coloros` |
| **Implements** | matrix rows #14, #21 |

## 1. Summary

ColorOS's current character: **luminous glass** — layered translucency with light
*emitted from surfaces* (gradients/glow) rather than HyperOS's *depth-through-blur*.
Motion is smooth and ease-based (confident, no bounce), with strong center-alignment
as a layout philosophy.

## 2. Tokens

| Token                   | Value (OpenStock original)                |
|-------------------------|-------------------------------------------|
| `corner.panel`          | 32 dp                                     |
| `corner.tile`           | full circle (toggles) / 24 dp (cards)     |
| `surface.glass`         | neutral @ 66% + top light-bleed gradient  |
| `surface.glow.active`   | accent @ 24% alpha, 12 dp blur            |
| `accent`                | OpenStock emerald (`#00A86B` light / `#2FD593` dark) — ours |
| `type.display`          | 26 sp / w5 — headers, centered            |
| `type.body`             | 15 sp / w4                                |
| `motion.standard`       | 320 ms ease-out-quint, zero overshoot     |
| `motion.press`          | scale 0.92, 180 ms spring-back            |
| `grid.columns`          | 4 (phone) / 6 (landscape)                 |

## 3. Layout philosophy rules

1. **Center-first**: panel content is horizontally centered; text headers centered.
2. **Light before shadow**: glow/gradients express state; drop shadows are avoided.
3. **No bounce**: motion is settled and confident; springs only for press feedback.

## 4. realme UI delta

See [realme-ui-notes.md](realme-ui-notes.md) — realme UI shares the layout engine with
different accent/palette defaults and no light-bleed; implemented as token overrides
over this spec's primitives.

## 5. Verification

- [ ] No spring overshoot anywhere in `coloros` skin containers.
- [ ] Glow only on active/affordance elements; never on static text.
- [ ] Center-alignment holds for all panel headers.

## 6. Provenance

- Observed on: public ColorOS 16/17 reviews, community posts, 2026-11.
- Method: token synthesis with OpenStock-original palette.
