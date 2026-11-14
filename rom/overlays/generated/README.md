# Generated overlay resources

Everything under `generated/` is produced by `tools/gen-rro.py` from
`specs/tokens/*.json`. Do not edit by hand — edit the tokens and regenerate.

## Binding to a real SystemUI (next phase)

These resources are OpenStock-namespaced (`os_*`). To make them *override*
a vendor-independent SystemUI, three things are needed:

1. **A pinned AOSP baseline** — the exact `android-16` tag the overlay targets.
2. **A binding table** — mapping `os_panel_corner` → the actual SystemUI resource
   name (e.g. shade corner radii) for that baseline.
3. **An `Android.bp` runtime_resource_overlay module** per skin, generated from
   the pack manifest (see `docs/skin-pack-format.md`).

Nothing here references vendor resources — vendor SystemUI resource names are
only discoverable from proprietary APKs, which LEGAL.md forbids reading.
