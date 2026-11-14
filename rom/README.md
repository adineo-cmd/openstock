# OpenStock ROM track — the token→RRO pipeline

The earlier "AOSP manifest + overlay skeleton" scaffolding was **removed on purpose**:
a repo manifest and a hand-written RRO are things every AOSP project has — they are not
what makes OpenStock different. What is different is this pipeline:

```
specs/tokens/hyperos.json  ─┐
specs/tokens/coloros.json   ├─► tools/gen-rro.py ─► rom/overlays/generated/<skin>/res/values/openstock_tokens.xml
specs/tokens/oneui.json    ─┘                          (validated by tokens-check.py first)
```

One token edit regenerates the entire ROM-facing surface. The generated resources are
OpenStock-namespaced (`os_*`) and committed, with CI failing if they drift from the tokens
(`gen-rro.py --check`).

## Getting from `os_*` resources to a working overlay

`generated/README.md` documents the three honest prerequisites: a pinned AOSP baseline
tag, a binding table mapping `os_*` names to real SystemUI resource names on that baseline,
and a generated `runtime_resource_overlay` module per skin. Nothing here reads vendor
resources — vendor resource names are only discoverable from proprietary APKs, which
[LEGAL.md](../LEGAL.md) forbids.

## What this directory deliberately does NOT contain

- No repo manifest — building "yet another AOSP fork" is not this project's contribution.
- No hand-written overlay examples — they rot; generated ones don't.
- No device trees — a later phase, only after the binding table exists.
