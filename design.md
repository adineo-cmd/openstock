# Design System

## Visual Style
- Typography: system stack in the demo; per-skin type roles from tokens (display 24–28 sp
  w5–6, body 15 sp w4–5, caption 12 sp w4). Vendor fonts are never used.
- Colors: per-skin palettes live in `specs/tokens/*.json` (`#RRGGBBAA`); OpenStock-original
  hex — OpenStock cyan for `hyperos`, emerald for `coloros`, indigo for `oneui`.
- Spacing: 360 dp reference width; px = dp × (screenPx / 360); panel corners 28–32 dp;
  tile sizes 52–64 dp; gaps 12–20 dp per skin.

## Components
- Pull panel (`PullPanel` + `PanelBehavior`) — drag-follow, two-stage (hyperos) or
  single-stage (coloros/oneui), glass surface, scrim, optional light bleed.
- Tiles — square 64 dp (hyperos), glowing circle 56 dp (coloros), labeled circle 52 dp
  (oneui); shared `TileState` model.
- Brightness slider + media card + notification list — shared primitives, skin-styled.
- Skin switcher — floating pill; runtime hot-swap.

## Patterns
- LiveActivity (SC-OS-001): one content model, three presentations.
- Tokens-first: any new visual value becomes a token before a composable reads it.
- Motion per skin: spring overshoot (hyperos) vs settled ease-out (coloros/oneui).

## Themes
- Dark-first demo; palettes carry light/dark roles in specs; dynamic color stays
  AOSP-native in the ROM track.
