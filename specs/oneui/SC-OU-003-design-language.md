# SC-OU-003: One UI skin design language (reachability-first)

| Field          | Value |
|----------------|-------|
| **ID**         | SC-OU-003 |
| **Source OS**  | One UI 8.x (observed behavior) |
| **Status**     | stable |
| **Priority**   | P1 |
| **OpenStock skin** | `oneui` |
| **Implements** | matrix rows #13, #19, #21 |

## 1. Summary

One UI's defining *idea* is **reachability-first composition**: content pushes to the
lower half of tall screens; the upper zone holds large, glanceable type and imagery.
The `oneui` skin applies this ordering to every OpenStock surface it renders.

## 2. Tokens

| Token               | Value (OpenStock original)        |
|---------------------|-----------------------------------|
| `corner.panel`      | 30 dp                             |
| `corner.toggle`     | full circle                       |
| `surface.panel`     | neutral @ 82%, mild 12 dp blur    |
| `accent`            | OpenStock indigo (`#4C6FFF` light / `#7D97FF` dark) — ours |
| `type.header`       | 24 sp / w6                        |
| `type.body`         | 15 sp / w4                        |
| `type.toggleLabel`  | 12 sp / w4                        |
| `motion.standard`   | 260 ms ease-out-cubic, no bounce  |
| `scrim.strength`    | 0.5 (stronger than other skins)   |
| `grid.columns`      | 3 default / 4 compact             |

## 3. Composition rules

1. **Thumb-zone weighting**: primary interactive content in the bottom 60% of any
   surface; informational content above.
2. **Type does the hierarchy**: big headers, small controls; minimum decorative chrome.
3. **Motion is quiet**: no bounce, no glow; state changes are calm fades/moves.
4. Iconography: squircle, flat single-color glyphs; themed (monochrome) icon mode
   supported natively.

## 4. Verification

- [ ] Every new `oneui` surface passes a "thumb-zone audit" (interactive centroid in
      bottom 60%).
- [ ] No spring overshoot / glow effects in `oneui` skin.
- [ ] Headers ≥ 22 sp and left-aligned.

## 5. Provenance

- Observed on: public One UI 8.x reviews/design docs, 2026-11.
- Method: token synthesis with OpenStock-original palette. Reachability ordering is a
  functional composition principle, not protectable expression.
