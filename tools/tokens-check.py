#!/usr/bin/env python3
import json
import sys
from pathlib import Path

REQUIRED_COLOR = ["accent", "accentOn", "surfaceGlass", "textPrimary",
                  "textSecondary", "tileInactiveFill", "tileInactiveIcon", "scrim"]
REQUIRED_SHAPE = ["panelCornerDp", "tileCornerDp", "cardCornerDp", "sliderCornerDp"]
REQUIRED_LAYOUT = ["columns", "tileSizeDp", "tileGapDp", "tileIconDp",
                   "tilesAreCircles", "tileLabel", "tileGlow"]
REQUIRED_PANEL = ["expandMode", "compactStop", "fullThreshold", "openThreshold",
                  "edgeInsetDp", "statusBarVisible", "lightBleed", "contentAnchor"]
REQUIRED_MOTION = ["type", "damping", "stiffness", "durationMs", "overshootAllowed"]

SKINS = {"hyperos", "coloros", "oneui"}
EXPAND_MODES = {"two-stage", "single-stage"}
ANCHORS = {"top", "bottom"}
MOTION_TYPES = {"spring", "tween"}


def is_hex8(value: str) -> bool:
    return isinstance(value, str) and value.startswith("#") and len(value) == 9 \
        and all(c in "0123456789abcdefABCDEF" for c in value[1:])


def check_file(path: Path, errors: list[str]) -> dict:
    def err(msg: str) -> None:
        errors.append(f"{path.name}: {msg}")

    try:
        data = json.loads(path.read_text(encoding="utf-8"))
    except (json.JSONDecodeError, OSError) as exc:
        err(f"unreadable/invalid JSON: {exc}")
        return {}

    if data.get("skin") not in SKINS:
        err(f"unknown skin {data.get('skin')!r}")
    if not data.get("specRefs"):
        err("missing specRefs (every token set must cite its specs)")

    color = data.get("color", {})
    for key in REQUIRED_COLOR:
        value = color.get(key)
        if value is None:
            err(f"color.{key} missing")
        elif not is_hex8(value):
            err(f"color.{key}={value!r} is not #RRGGBBAA")

    shape = data.get("shape", {})
    for key in REQUIRED_SHAPE:
        v = shape.get(key)
        if not isinstance(v, (int, float)) or not 0 <= v <= 64:
            err(f"shape.{key}={v!r} out of range 0..64")

    layout = data.get("layout", {})
    for key in REQUIRED_LAYOUT:
        if key not in layout:
            err(f"layout.{key} missing")
    if layout.get("columns") not in (3, 4, 5, 6):
        err(f"layout.columns={layout.get('columns')!r} unusual (expected 3-6)")
    if layout.get("tilesAreCircles") and (shape.get("tileCornerDp") or 0) * 2 < layout.get("tileSizeDp", 0):
        err("circles should not need tileCornerDp >= tileSize/2 (corner ignored)")
    if layout.get("tileGlow") and not layout.get("tilesAreCircles"):
        err("tileGlow is currently specced for circular tiles only (SC-CO-001)")

    panel = data.get("panel", {})
    for key in REQUIRED_PANEL:
        if key not in panel:
            err(f"panel.{key} missing")
    if panel.get("expandMode") not in EXPAND_MODES:
        err(f"panel.expandMode={panel.get('expandMode')!r} invalid")
    if panel.get("contentAnchor") not in ANCHORS:
        err(f"panel.contentAnchor={panel.get('contentAnchor')!r} invalid")
    for key in ("compactStop", "fullThreshold", "openThreshold"):
        v = panel.get(key)
        if not isinstance(v, (int, float)) or not 0 < v <= 1:
            err(f"panel.{key}={v!r} must be a fraction in (0, 1]")
    if panel.get("expandMode") == "single-stage" and panel.get("statusBarVisible") is False \
            and panel.get("edgeInsetDp") not in (0, None):
        err("single-stage full-bleed panels should have edgeInsetDp 0")

    motion = data.get("motion", {})
    for key in REQUIRED_MOTION:
        if key not in motion:
            err(f"motion.{key} missing")
    if motion.get("type") not in MOTION_TYPES:
        err(f"motion.type={motion.get('type')!r} invalid")
    if motion.get("type") == "spring" and not 0 < motion.get("damping", 1) < 1:
        err("spring damping must be < 1 for overshoot (SC-HX-003)")
    if motion.get("type") == "tween" and motion.get("damping") != 1.0:
        err("tween damping must be 1.0 (no overshoot)")
    if motion.get("overshootAllowed") != (motion.get("type") == "spring"):
        err("overshootAllowed must match motion type (spring=true, tween=false)")
    if motion.get("type") == "tween" and not motion.get("easing"):
        err("tween motion requires an easing curve string")

    return data


def cross_check(tokens: dict[str, dict], errors: list[str]) -> None:
    hx = tokens.get("hyperos", {})
    if hx:
        hx_panel = hx.get("panel", {})
        if hx_panel.get("expandMode") != "two-stage":
            errors.append("cross: hyperos must be two-stage (SC-HX-001 §1)")
        if hx_panel.get("statusBarVisible") is not True:
            errors.append("cross: hyperos compact panel leaves the status bar visible (SC-HX-001 §2)")
    for name, data in tokens.items():
        panel, layout = data.get("panel", {}), data.get("layout", {})
        if name != "hyperos" and panel.get("expandMode") == "two-stage":
            errors.append(f"cross: {name} specced as single-stage (SC-CO-001 / SC-OU-001)")
        if name == "oneui" and not layout.get("tileLabel"):
            errors.append("cross: oneui toggles carry labels (SC-OU-001 §3)")
        if name == "coloros" and not data.get("panel", {}).get("lightBleed"):
            errors.append("cross: coloros requires lightBleed (SC-CO-001 §3)")


def main() -> int:
    tokens_dir = Path(sys.argv[1] if len(sys.argv) > 1 else Path(__file__).resolve().parent.parent / "specs" / "tokens")
    files = sorted(tokens_dir.glob("*.json"))
    if not files:
        print(f"FAIL: no token files found in {tokens_dir}")
        return 1

    errors: list[str] = []
    tokens: dict[str, dict] = {}
    for path in files:
        data = check_file(path, errors)
        if data.get("skin"):
            tokens[data["skin"]] = data
    cross_check(tokens, errors)

    for e in errors:
        print(f"  ERROR {e}")
    if errors:
        print(f"FAIL: {len(errors)} error(s) across {len(files)} token file(s)")
        return 1
    print(f"PASS: {len(files)} token file(s) valid ({', '.join(sorted(tokens))})")
    return 0


if __name__ == "__main__":
    sys.exit(main())
