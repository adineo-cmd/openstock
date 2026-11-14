package com.openstock.launcher.skin

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.Easing
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class ExpandMode { TwoStage, SingleStage }

enum class ContentAnchor { Top, Bottom }

data class MotionSpec(
    val useSpring: Boolean,
    val damping: Float,
    val stiffness: Float,
    val durationMs: Int,
    val easing: Easing,
)

data class PanelBehavior(
    val expandMode: ExpandMode,

    val compactStop: Float = 0.45f,

    val fullThreshold: Float = 0.55f,

    val openThreshold: Float = 0.32f,
    val cornerRadius: Dp = 28.dp,
    val glassColor: Color,
    val scrimStrength: Float = 0.40f,

    val edgeInset: Dp = 12.dp,

    val lightBleed: Boolean = false,
    val anchor: ContentAnchor = ContentAnchor.Top,
)

data class Palette(
    val accent: Color,
    val surfaceGlass: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val tileActiveIcon: Color,
    val tileInactiveFill: Color,
    val tileInactiveIcon: Color,
)

data class ShapeSpec(
    val panelCorner: Dp,

    val tileCorner: Dp,
    val cardCorner: Dp,
    val sliderCorner: Dp,
)

data class LayoutSpec(
    val columns: Int,
    val tileSize: Dp,
    val tileGap: Dp,
    val tileIcon: Dp,
    val tilesAreCircles: Boolean,

    val tileLabel: Boolean = false,

    val tileGlow: Boolean = false,
)

data class SkinSpec(
    val name: String,

    val specIds: List<String>,
    val palette: Palette,
    val shapes: ShapeSpec,
    val layout: LayoutSpec,
    val panel: PanelBehavior,
    val motion: MotionSpec,
)

val EaseOutQuint: Easing = CubicBezierEasing(0.22f, 1f, 0.36f, 1f)
val EaseOutCubic: Easing = CubicBezierEasing(0.33f, 1f, 0.68f, 1f)
