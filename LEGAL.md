# Legal & Clean-Room Policy

**This document is binding on every contributor.** PRs that violate it will be closed without
review, and offending content will be removed.

## What OpenStock is

An independent, clean-room reimplementation of *interaction patterns* found in various Android
stock operating systems, using only original expression (code, assets, artwork, sounds, text).

## The bright line

We **never** include, in any form — commits, issues, wiki, screenshots in docs, binaries:

1. **Decompiled / disassembled code** from any proprietary ROM (HyperOS, ColorOS, realme UI,
   One UI, or any other), including smali, Jadx output, framework dumps, or `.odex`/`.vdex` extracts.
2. **Proprietary binaries**: OEM APKs, firmware images, boot/vendor partitions, OTA zips.
3. **Proprietary assets**: vendor icons, wallpapers, animations, ringtones, fonts
   (MiSans, OPPO Sans, SamsungOne), Lottie/`XML` animations extracted from vendor APKs,
   even "re-encoded" or traced bit-for-bit copies of them.
4. **Confidential material**: leaked builds, internal docs, NDA'd information.
5. **Trademarks** in product names, app names, or iconography. Comparisons like
   *"this implements the HyperOS-style panel from SC-HX-001"* are fine; branding a skin
   "MIUI" or using vendor logos is not.

## Where our knowledge comes from (allowed sources)

- **Direct observation**: using a stock device you own as a user — watching what the UI does,
  measuring behavior with a stopwatch, screen recorder, ruler, or color picker.
- **Public, vendor-published material**: user manuals, official feature pages, press decks.
- **Public third-party analysis**: news articles, video reviews, community documentation
  (these describe *behavior*, which is what we spec).
- **AOSP itself**: Apache-2.0, fully reusable with attribution.

## Where it must NOT come from (forbidden sources)

- Decompilers (Jadx, Ghidra/IDA on vendor APKs, apktool output) *against vendor software*.
  (Using these tools on OpenStock's own code is fine.)
- Extracted vendor resources (`resources.arsc`, `res/` inside OEM APKs).
- Vendor source leaks of any vintage.

## Why this works legally (short version)

Copyright protects **expression** (code, pixels, sounds) — not **ideas, features, or
functional behavior**. Interaction design (e.g., "a two-stage pull-down shade with glass tiles
and spring animation") is an unprotectable idea; a specific implementation of it is protectable.
By specifying *behavior* from observation and implementing it from the spec with original
expression, OpenStock follows the same clean-room practice that let independent projects
reimplement APIs and UIs for decades. This is not legal advice; maintainers may adjust this
policy as needed.

### Trade dress / counterfeit note

Behavior parity has a limit: OpenStock must never present itself as a vendor product.
We match *functional* behavior and measured geometry, but always with our own name, our
own icon set, our own wallpaper, and visible OpenStock branding. Deliberately imitating a
product's total look to deceive users into believing it is the vendor's official software
is a trade-dress problem no clean-room process cures. If a skin ever feels like a
counterfeit rather than an homage-with-original-parts, that is a bug.

## Trademark notice

Xiaomi, HyperOS, MIUI, OPPO, ColorOS, OnePlus, OxygenOS, realme, realme UI, Samsung, One UI,
Google, and Android are trademarks of their respective owners. OpenStock is not affiliated
with, endorsed by, or sponsored by any of them. Names are used descriptively to identify
which stock OS a spec documents.

## If you spot a violation

Open an issue titled `[LEGAL]` describing the file/commit. Do not link to or quote the
infringing content in the issue body. Maintainers will remove it within 72 hours.
