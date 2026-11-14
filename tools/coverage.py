#!/usr/bin/env python3
import json
import re
import sys
from pathlib import Path

repo = Path(__file__).resolve().parent.parent


def collect_specs() -> list[dict]:
    specs = []
    for path in sorted((repo / "specs").rglob("SC-*.md")):
        text = path.read_text(encoding="utf-8")
        spec_id = re.search(r"\|\s*\*\*ID\*\*\s*\|\s*(SC-[A-Z]{2}-\d{3})", text)
        status = re.search(r"\|\s*\*\*Status\*\*\s*\|\s*(\w+)", text)
        priority = re.search(r"\|\s*\*\*Priority\*\*\s*\|\s*(P\d)", text)
        if spec_id:
            specs.append({
                "id": spec_id.group(1),
                "status": (status.group(1) if status else "unknown").lower(),
                "priority": priority.group(1) if priority else "?",
                "unmeasured": len(re.findall(r"\|\s*~\s*\|", text)),
            })
    return specs


def collect_implemented() -> set[str]:
    found = set()
    for path in (repo / "app").rglob("*.kt"):
        found.update(re.findall(r"SC-[A-Z]{2}-\d{3}", path.read_text(encoding="utf-8")))
    return found


def matrix_rows() -> int:
    text = (repo / "docs" / "feature-matrix.md").read_text(encoding="utf-8")
    return len(re.findall(r"^\|\s*\d+\s*\|", text, flags=re.MULTILINE))


def badge(specs: list[dict], implemented: set[str], rows: int) -> str:
    stable = sum(1 for s in specs if s["status"] == "stable")
    label, value = "spec coverage", f"{len(specs)} specs · {stable} stable · {len(implemented)} impl · {rows} matrix"
    lw, vw = len(label) * 7 + 14, len(value) * 7 + 14
    width = lw + vw
    return (
        f'<svg xmlns="http://www.w3.org/2000/svg" width="{width}" height="20" role="img" '
        f'aria-label="{label}: {value}">'
        f'<title>{label}: {value}</title>'
        f'<linearGradient id="s" x2="0" y2="100%"><stop offset="0" stop-color="#bbb" stop-opacity=".1"/>'
        f'<stop offset="1" stop-opacity=".1"/></linearGradient>'
        f'<clipPath id="r"><rect width="{width}" height="20" rx="3" fill="#fff"/></clipPath>'
        f'<g clip-path="url(#r)">'
        f'<rect width="{lw}" height="20" fill="#555"/>'
        f'<rect x="{lw}" width="{vw}" height="20" fill="#0a7d32"/>'
        f'<rect width="{width}" height="20" fill="url(#s)"/></g>'
        f'<g fill="#fff" text-anchor="middle" font-family="Verdana,Geneva,DejaVu Sans,sans-serif" '
        f'font-size="110" text-rendering="geometricPrecision">'
        f'<text x="{lw / 2}" y="150" transform="scale(.1)" fill="#fff">{label}</text>'
        f'<text x="{lw + vw / 2}" y="150" transform="scale(.1)" fill="#fff">{value}</text></g>'
        f"</svg>"
    )


def main() -> int:
    specs = collect_specs()
    implemented = collect_implemented()
    rows = matrix_rows()
    svg = badge(specs, implemented, rows)

    out_path = repo / "docs" / "coverage.svg"
    if "--check" in sys.argv:
        if not out_path.exists() or out_path.read_text(encoding="utf-8") != svg:
            print("FAIL: docs/coverage.svg is stale — run `python3 tools/coverage.py`")
            return 1
        print("PASS: coverage badge up to date")
        return 0

    out_path.write_text(svg, encoding="utf-8")
    print(f"wrote {out_path}")
    print()
    print("| Metric | Value |")
    print("|---|---|")
    print(f"| Specs (SC-*) | {len(specs)} |")
    for status in ("stable", "draft"):
        n = sum(1 for s in specs if s["status"] == status)
        if n:
            print(f"| Status: {status} | {n} |")
    print(f"| Implemented in app | {len(implemented)} ({', '.join(sorted(implemented)) or '—'}) |")
    print(f"| Feature-matrix rows | {rows} |")
    print(f"| Unmeasured value markers (~) | {sum(s['unmeasured'] for s in specs)} |")
    missing = sorted(s["id"] for s in specs if s["id"] not in implemented)
    print()
    print(f"Specced but not yet implemented: {', '.join(missing) or 'none'}")
    return 0


if __name__ == "__main__":
    sys.exit(main())
