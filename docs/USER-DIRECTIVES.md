# Owner directives — chronological record

Standing instructions the owner has given for this project. Agents: treat these as
binding; newer ones override older ones where they conflict.

## 2026-11-10 — original brief

- "Reverse engineer every mobile stock OS of different companies like realme, Xiaomi etc
  and make an open source version."
- Chosen scope (via clarification): **full project + prototype**, targeting **all OEMs at
  once, matrix first** (HyperOS, ColorOS/realme UI, One UI).

## 2026-11-11 — fidelity + uniqueness

- "The UI should look the same" — match the stock OSes exactly; wanted a skill/MCP from a
  popular GitHub repo to make it match; reverse engineer every OS to understand its
  underlying structure; make it open source; make the project more unique.
- Resolution: pixel-identical cloning is legally impossible (no tool changes that);
  behavior/geometry/motion MUST match exactly via measured tokens; artwork stays original.
  "Underlying structure" satisfied legally via the Apache-2.0 AOSP SystemUI ancestor.
- Standing fidelity policy: tokens are measured numbers; never vendor art/names; never
  present OpenStock as a counterfeit vendor product.

## 2026-11-11 — differentiation

- "What is unique in this other than other AOSP projects?" — demanded an honest
  differentiation analysis, then:
- "Remove that which is not unique and continue to build the unique one if no one has
  made it — research if no one has made it."
- Result: generic AOSP scaffolding removed; prior-art research run (nothing combines
  spec corpus + token format + multi-skin engine for phone OEM UX); unique toolchain
  built (skindiff, coverage badge, gen-rro, skin-pack format, SC-OS-001 LiveActivity).

## 2026-11-14 — publishing

- "Push it to GitHub **adineo-cmd**" — explicit account choice for this repo (owner has
  two accounts; never use the other without asking).
- "Also the AGENTS.md and other md on what to do in future or things that are pending."
- "Also push all the instructions that I have told you to do" — this file is that record.

## Standing global rules (owner's global config, applies to all their projects)

- No comments in any file; never add new comments.
- Maintain prd.md / architecture.md / design.md / rules.md / task.md / progress.md every
  session; templates in `~/.zcode/project-templates/`.
- Two GitHub accounts (`adineo-cmd` personal, `adineo-cmd2` work) — always ask which one
  before any GitHub write; for OpenStock the owner has answered: `adineo-cmd`.
