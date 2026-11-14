# realme UI 8 — deltas from ColorOS (SC-CO base)

realme UI is built on the ColorOS base; its behavior is a **token overlay**, not a
separate spec set. This file records the deltas observed publicly. Each delta gets a
spec ID `SC-RM-NNN` only if it becomes a structural (not token) difference.

| Aspect           | ColorOS 17 base        | realme UI 8 delta                  | Type     |
|------------------|------------------------|------------------------------------|----------|
| Quick settings   | SC-CO-001              | same layout; no top light-bleed; flatter glass | token |
| Accent default   | emerald (ours: `#00A86B`) | warmer yellow/gold family (ours: `#D9A514`) | token |
| Animations       | SC-CO-003 motion       | same curves, ~10% faster settle    | token    |
| Always-on display| luminous styles        | different style set, same mechanism| token    |
| Sidebar          | SC-CO-002 Smart Sidebar| same feature, "Sidebar" naming     | structural-none |
| Home folders     | large-folder styles    | same                               | none     |

Rule: an implementation PR that touches realme UI behavior must **not** fork the
ColorOS composables — it must pass a `realmeUiTokens` override into the shared
`coloros` primitives. If a true structural delta is observed on a device, file a new
`SC-RM-NNN` spec.

## Provenance

- Observed on: public realme UI 7/8 rollout coverage and community reports, 2026-11.
- Method: behavioral synthesis; realme-specific measurements pending device access.
