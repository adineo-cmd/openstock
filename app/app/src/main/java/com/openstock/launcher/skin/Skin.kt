package com.openstock.launcher.skin

enum class SkinType(val label: String) {
    HYPEROS("hyperos"),
    COLOROS("coloros"),
    ONEUI("oneui"),
}

fun nextSkin(current: SkinType): SkinType =
    SkinType.entries[(current.ordinal + 1) % SkinType.entries.size]
