# The OpenStock Skin Pack format (v0.1)

A **skin pack** is the unit of distribution: one folder (or zip) that carries everything
needed to present OpenStock as one vendor-style experience — tokens, spec references,
and original artwork. This format is unique to OpenStock: vendor skins can't be packaged
this way (their "skin" is a code fork), and custom-ROM themes are resource dumps without
a behavior contract.

## Layout

```
mypack/
├── pack.json            # manifest (required)
├── tokens.json          # skin tokens — must pass tools/tokens-check.py
├── specs/               # behavior specs this pack implements or cites (SC-*.md)
├── artwork/             # ORIGINAL artwork only; each file's license in LICENSE.md
└── MOTION.md            # optional: narrative of the pack's motion character
```

## pack.json

```json
{
  "id": "com.example.openstock.mypack",
  "name": "My Pack",
  "version": "0.1.0",
  "license": "Apache-2.0",
  "specRefs": ["SC-HX-001", "SC-OS-001"],
  "originals": ["accent palette", "wallpapers"],
  "targets": ["app", "web", "rro"]
}
```

- `id`: reverse-DNS; vendor names only in *descriptive* roles (LEGAL.md), never as branding.
- `specRefs`: the behavior contract — every surface the pack renders must cite a spec.
- `targets`: which consumers the pack claims to support (`app`, `web`, `rro`).

## Validation pipeline (what CI runs on every pack)

1. `python3 tools/tokens-check.py <dir>/tokens.json` — schema + cross-spec consistency.
2. Target support checks:
   - `app` — tokens must map 1:1 onto `SkinSpec` fields (codegen: next phase).
   - `rro` — `python3 tools/gen-rro.py <dir>/tokens.json` must succeed.
   - `web` — a `web.js` bridge in the demo's `TOKENS` shape.
3. **Originality gate**: every `artwork/` file carries a license line; packs referencing
   vendor asset names fail.
4. `tools/skindiff.py` generates a report against the nearest stock skin and ships with
   the pack, so users see exactly where a pack deviates from a spec.

## Why packs, not forks

A vendor skin in the ROM world is a code fork you must rebase forever. An OpenStock pack
is data + specs: it survives AOSP rebases untouched, diffs mechanically (`skindiff`),
installs into any consumer, and can be authored by someone who owns a device we don't —
which is how coverage of FunTouch, Nothing OS, etc. is supposed to happen.
