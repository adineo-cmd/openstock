#!/usr/bin/env python3
import json
import sys
from pathlib import Path

repo = Path(__file__).resolve().parent.parent
tokens_dir = repo / "specs" / "tokens"
out_root = repo / "rom" / "overlays" / "generated"


def snake(name: str) -> str:
    return "os_" + "".join("_" + c.lower() if c.isupper() else c for c in name).lstrip("_")


def to_aarrggbb(value: str) -> str:
    return "#" + value[7:9] + value[1:7]


def walk(data: dict, prefix: str = ""):
    for key, value in data.items():
        path_key = f"{prefix}.{key}" if prefix else key
        if isinstance(value, dict):
            yield from walk(value, path_key)
        else:
            yield path_key, key, value


def resources_for(tokens: dict) -> str:
    colors, dimens, fractions, bools, integers = [], [], [], [], []
    DIMENS = {"panelCornerDp", "tileCornerDp", "cardCornerDp", "sliderCornerDp",
              "tileSizeDp", "tileGapDp", "tileIconDp", "edgeInsetDp"}
    FRACTIONS = {"compactStop", "fullThreshold", "openThreshold"}
    INTEGERS = {"columns", "durationMs", "stiffness"}
    BOOLS = {"tilesAreCircles", "tileLabel", "tileGlow", "statusBarVisible", "lightBleed", "overshootAllowed"}

    for path_key, key, value in walk(tokens):
        name = snake(path_key.replace(".", "_"))
        if key in BOOLS or isinstance(value, bool):
            bools.append(f'    <bool name="{name}">{"true" if value else "false"}</bool>')
        elif key in FRACTIONS:
            fractions.append(f'    <item type="fraction" name="{name}">{round(float(value) * 100)}%</item>')
        elif key in DIMENS and isinstance(value, (int, float)):
            dimens.append(f'    <dimen name="{name}">{value}dp</dimen>')
        elif key in INTEGERS:
            integers.append(f'    <integer name="{name}">{int(value)}</integer>')
        elif isinstance(value, str) and value.startswith("#") and len(value) == 9:
            colors.append(f'    <color name="{name}">{to_aarrggbb(value)}</color>')
        elif isinstance(value, str):
            strings_val = value.replace("&", "&amp;").replace("<", "&lt;")
            integers.append(f'    <string name="{name}">{strings_val}</string>')

    parts = ['<?xml version="1.0" encoding="utf-8"?>',
             "<resources>"]
    if colors:
        parts += [""] + colors
    if dimens:
        parts += [""] + dimens
    if fractions:
        parts += [""] + fractions
    if bools:
        parts += [""] + bools
    if integers:
        parts += [""] + integers
    parts += ["</resources>", ""]
    return "\n".join(parts)


def main() -> int:
    check = "--check" in sys.argv
    generated: dict[str, str] = {}
    for path in sorted(tokens_dir.glob("*.json")):
        tokens = json.loads(path.read_text(encoding="utf-8"))
        generated[tokens["skin"]] = resources_for(tokens)

    stale = False
    for skin, content in generated.items():
        target = out_root / skin / "res" / "values" / "openstock_tokens.xml"
        if check:
            if not target.exists() or target.read_text(encoding="utf-8") != content:
                print(f"FAIL: {target} is stale — run `python3 tools/gen-rro.py`")
                stale = True
            continue
        target.parent.mkdir(parents=True, exist_ok=True)
        target.write_text(content, encoding="utf-8")
        print(f"wrote {target}")

    if stale:
        return 1
    if not check:
        readme = out_root / "README.md"
        readme.write_text(
            "# Generated overlay resources\n\n"
            "Everything under `generated/` is produced by `tools/gen-rro.py` from\n"
            "`specs/tokens/*.json`. Do not edit by hand — edit the tokens and regenerate.\n\n"
            "## Binding to a real SystemUI (next phase)\n\n"
            "These resources are OpenStock-namespaced (`os_*`). To make them *override*\n"
            "a vendor-independent SystemUI, three things are needed:\n\n"
            "1. **A pinned AOSP baseline** — the exact `android-16` tag the overlay targets.\n"
            "2. **A binding table** — mapping `os_panel_corner` → the actual SystemUI resource\n"
            "   name (e.g. shade corner radii) for that baseline.\n"
            "3. **An `Android.bp` runtime_resource_overlay module** per skin, generated from\n"
            "   the pack manifest (see `docs/skin-pack-format.md`).\n\n"
            "Nothing here references vendor resources — vendor SystemUI resource names are\n"
            "only discoverable from proprietary APKs, which LEGAL.md forbids reading.\n",
            encoding="utf-8",
        )
        print(f"wrote {readme}")
    return 0


if __name__ == "__main__":
    sys.exit(main())
