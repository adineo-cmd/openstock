# AGENTS.md — working rules for agents on OpenStock

## What this project is

OpenStock is a clean-room, Apache-2.0 reimplementation of the signature UX of OEM Android
skins (HyperOS, ColorOS, realme UI, One UI). Skins are **data** (`specs/tokens/*.json`)
consumed by one engine: a Compose app, a web demo, and generated Android overlay
resources. The unique assets are the spec corpus, the token format, and the toolchain
(`skindiff`, `coverage`, `gen-rro`, `tokens-check`). No other open project combines these
(prior-art checked 2026-11-11; nearest neighbor is Android Automotive's OEM design
tokens, which is closed-set and automotive-only).

## Non-negotiable gates (read before touching anything)

1. [LEGAL.md](LEGAL.md) — never include decompiled vendor code, vendor binaries, vendor
   artwork, or trademarks. Observe devices as a user; spec behavior; implement from specs.
2. [docs/methodology.md](docs/methodology.md) — specs before code; every implementation
   cites a spec ID (`SC-XX-NNN`); unmeasured values are marked `~` in specs, never invented.
3. No comments in any file (owner's global rule). Explain in PRs/chat, not in code.
4. Tokens are canonical: change `specs/tokens/*.json`, then regenerate every consumer and
   commit the outputs together.

## Session protocol

```bash
python3 -I tools/tokens-check.py          # token schema + cross-spec consistency
python3 -I tools/coverage.py --check      # spec-coverage badge freshness
python3 -I tools/gen-rro.py --check       # generated RRO resources freshness
for f in docs/diffs/*.md; do              # skindiff report freshness
  a=$(basename "$f" .md | cut -d- -f1); b=$(basename "$f" .md | sed 's/.*-vs-//')
  python3 -I tools/skindiff.py "$a" "$b" --check "$f"
done
```

All must pass before any PR/commit. If a token edit makes generated artifacts stale,
regenerate (`coverage.py`, `skindiff.py --all --out-dir docs/diffs`, `gen-rro.py`) and
commit them with the token change.

## Where things live

| Path                | Meaning |
|---------------------|---------|
| `specs/`            | Behavior specs (`SC-XX-NNN`), one dir per source OS + `openstock/` for originals |
| `specs/tokens/`     | Canonical skin tokens — single source of truth |
| `tools/`            | `tokens-check.py`, `skindiff.py`, `coverage.py`, `gen-rro.py` |
| `app/`              | Kotlin/Compose reference implementation (skin switcher) |
| `rom/overlays/generated/` | Per-skin RRO resources, regenerated from tokens |
| `docs/diffs/`       | Committed skindiff reports |
| `docs/structure/`   | AOSP SystemUI architecture mapping (Apache-2.0 ancestor) |
| `docs/USER-DIRECTIVES.md` | The owner's standing instructions, chronological |

## Publishing

- GitHub account for this repo: **`adineo-cmd`** (owner's explicit choice, 2026-11-14).
  The owner has two accounts; never push elsewhere without asking.
- CI (`.github/workflows/ci.yml`) enforces the gates above plus a decompiler-artifact
  tripwire; keep it green.

## Pending work (backlog → see task.md)

Highest-value first: device measurements for the 51 `~` spec markers; compile the app
(needs Android SDK; `gradle wrapper --gradle-version 8.9` first); SkinSpec codegen from
tokens (removes the last hand-synced consumer); skin-pack loader in the app; RRO binding
table against a pinned AOSP 16 baseline; LiveActivity primitive implementation (SC-OS-001).
