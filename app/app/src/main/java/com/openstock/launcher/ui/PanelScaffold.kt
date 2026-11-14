package com.openstock.launcher.ui

import androidx.compose.animation.core.animate
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.openstock.launcher.skin.ContentAnchor
import com.openstock.launcher.skin.ExpandMode
import com.openstock.launcher.skin.PanelBehavior

enum class PanelStage { Compact, Full }

class PanelState(private val behavior: PanelBehavior) {

    var progress by mutableFloatStateOf(0f)
        private set

    var settleTarget by mutableStateOf<Float?>(null)
        private set

    val isOpen: Boolean get() = progress > 0.02f

    fun stageFor(): PanelStage =
        if (behavior.expandMode == ExpandMode.TwoStage && progress in 0.02f..0.9f) {
            PanelStage.Compact
        } else {
            PanelStage.Full
        }

    fun dragBy(fraction: Float) {
        settleTarget = null
        progress = (progress + fraction).coerceIn(0f, 1f)
    }

    fun requestSettle(target: Float) {
        settleTarget = target
    }

    fun open() = requestSettle(1f)
    fun close() = requestSettle(0f)

    fun settleFromRelease(velocityHint: Float) {
        val target = when (behavior.expandMode) {
            ExpandMode.TwoStage -> when {
                velocityHint < -0.2f -> 0f
                progress >= behavior.fullThreshold -> 1f
                progress > behavior.openThreshold * 0.8f -> behavior.compactStop
                else -> 0f
            }
            ExpandMode.SingleStage -> when {
                velocityHint < -0.4f -> 0f
                progress > behavior.openThreshold -> 1f
                else -> 0f
            }
        }
        requestSettle(target)
    }

    internal fun applyProgress(v: Float) {
        progress = v
    }

    internal fun clearSettle() {
        settleTarget = null
    }
}

@Composable
fun PullPanel(
    behavior: PanelBehavior,
    motion: com.openstock.launcher.skin.MotionSpec,
    state: PanelState,
    content: @Composable (PanelStage) -> Unit,
) {
    BoxWithConstraints(Modifier.fillMaxSize()) {
        val screenHeight = maxHeight
        val density = LocalDensity.current
        val dragRange = with(density) { (screenHeight * 0.85f).toPx() }

        var topEdgeDy by remember { mutableFloatStateOf(0f) }
        var panelDy by remember { mutableFloatStateOf(0f) }

        LaunchedEffect(state.settleTarget) {
            val target = state.settleTarget ?: return@LaunchedEffect
            val m = motion
            if (m.useSpring) {
                animate(
                    initialValue = state.progress,
                    targetValue = target,
                    animationSpec = spring(dampingRatio = m.damping, stiffness = m.stiffness),
                ) { v, _ -> state.applyProgress(v) }
            } else {
                animate(
                    initialValue = state.progress,
                    targetValue = target,
                    animationSpec = tween(durationMillis = m.durationMs, easing = m.easing),
                ) { v, _ -> state.applyProgress(v) }
            }
            state.clearSettle()
        }

        if (state.isOpen) {
            Box(
                Modifier
                    .fillMaxSize()
                    .graphicsLayer { alpha = (state.progress * 1.6f).coerceAtMost(1f) }
                    .background(Color.Black.copy(alpha = behavior.scrimStrength))
                    .clickable { state.close() },
            )
        }

        Box(
            Modifier
                .fillMaxWidth()
                .height(72.dp)
                .align(Alignment.TopCenter)
                .pointerInput(behavior) {
                    detectVerticalDragGestures(
                        onVerticalDrag = { _, dy ->
                            topEdgeDy = dy
                            state.dragBy(dy / dragRange)
                        },
                        onDragEnd = {
                            state.settleFromRelease(if (topEdgeDy > 0f) 0.5f else -0.5f)
                        },
                    )
                },
        )

        if (state.progress > 0.005f) {
            Box(
                Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
                    .padding(horizontal = behavior.edgeInset)
                    .height(screenHeight * state.progress)
                    .graphicsLayer {
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(behavior.cornerRadius)
                        clip = true
                    }
                    .background(behavior.glassColor)
                    .pointerInput(behavior) {
                        detectVerticalDragGestures(
                            onVerticalDrag = { _, dy ->
                                panelDy = dy
                                if (dy < 0f) state.dragBy(dy / dragRange)
                            },
                            onDragEnd = {
                                state.settleFromRelease(if (panelDy < 0f) -0.5f else 0.2f)
                            },
                        )
                    },
            ) {
                if (behavior.lightBleed) {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .background(
                                Brush.verticalGradient(
                                    listOf(Color.White.copy(alpha = 0.12f), Color.Transparent),
                                ),
                            ),
                    )
                }
                when (behavior.anchor) {
                    ContentAnchor.Top -> Column(Modifier.fillMaxSize()) { content(state.stageFor()) }
                    ContentAnchor.Bottom -> Column(Modifier.fillMaxSize()) {
                        Spacer(Modifier.weight(1f))
                        content(state.stageFor())
                    }
                }
            }
        }
    }
}
