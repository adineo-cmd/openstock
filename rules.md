# Rules

## Coding Standards
- No comments anywhere (owner's global rule); explain in PRs/chat instead.
- Kotlin + Compose, single module; skins consume `SkinSpec` + shared `ui/` primitives only.
- Python tools: stdlib-only, run with `python3 -I`; deterministic output for `--check` modes.
- Generated artifacts are committed; generators must be idempotent.

## Review Rules
- Legal gate first: any vendor code/asset/trademark reference blocks the PR (LEGAL.md).
- Spec gate: implementations must cite spec IDs and match spec numbers.
- Freshness gate: tokens, badge, diffs, RRO outputs must be regenerated together.
- Skindiff reports must be regenerated when tokens change.

## Constraints
- Never decompile vendor software; never redistribute vendor binaries/assets.
- No GMS in the ROM track; no vendor trademarks in names/branding.
- GitHub publishing account for this repo: `adineo-cmd` (owner's explicit choice).
