package com.openstock.launcher.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.openstock.launcher.skin.SkinSpec

data class MockTile(val name: String, val icon: ImageVector, val initialOn: Boolean = false)

class TileState(val tile: MockTile) {
    var on by mutableStateOf(tile.initialOn)
        private set

    fun toggle() {
        on = !on
    }
}

@Composable
fun rememberTileStates(tiles: List<MockTile>, key: Any?): List<TileState> =
    remember(key) { tiles.map(::TileState) }

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SquareTile(
    state: TileState,
    spec: SkinSpec,
    modifier: Modifier = Modifier,
    onLongPress: () -> Unit = {},
) {
    val bg = if (state.on) spec.palette.accent else spec.palette.tileInactiveFill
    val tint = if (state.on) spec.palette.tileActiveIcon else spec.palette.tileInactiveIcon
    Box(
        modifier
            .size(spec.layout.tileSize)
            .clip(RoundedCornerShape(spec.shapes.tileCorner))
            .background(bg)
            .combinedClickable(onClick = { state.toggle() }, onLongClick = onLongPress),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            state.tile.icon,
            contentDescription = state.tile.name,
            tint = tint,
            modifier = Modifier.size(spec.layout.tileIcon),
        )
    }
}

@Composable
fun CircleToggle(
    state: TileState,
    spec: SkinSpec,
    modifier: Modifier = Modifier,
) {
    val bg = if (state.on) spec.palette.accent else spec.palette.tileInactiveFill
    val tint = if (state.on) spec.palette.tileActiveIcon else spec.palette.tileInactiveIcon
    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Box {
            if (spec.layout.tileGlow && state.on) {
                Box(
                    Modifier
                        .align(Alignment.Center)
                        .size(spec.layout.tileSize + 18.dp)
                        .blur(10.dp)
                        .background(spec.palette.accent.copy(alpha = 0.35f), CircleShape),
                )
            }
            Box(
                Modifier
                    .size(spec.layout.tileSize)
                    .clip(CircleShape)
                    .background(bg)
                    .clickable { state.toggle() },
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    state.tile.icon,
                    contentDescription = state.tile.name,
                    tint = tint,
                    modifier = Modifier.size(spec.layout.tileIcon),
                )
            }
        }
        if (spec.layout.tileLabel) {
            Spacer(Modifier.height(6.dp))
            Text(
                state.tile.name,
                color = spec.palette.textSecondary,
                fontSize = 12.sp,
                fontWeight = FontWeight(400),
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
fun ToggleGrid(
    states: List<TileState>,
    columns: Int,
    centered: Boolean = false,
    tile: @Composable (TileState) -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = if (centered) Alignment.CenterHorizontally else Alignment.Start,
    ) {
        states.chunked(columns).forEach { row ->
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                row.forEach { tile(it) }
            }
        }
    }
}

@Composable
fun BrightnessSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    accent: Color,
    trackColor: Color,
    icon: ImageVector,
    iconTint: Color,
    modifier: Modifier = Modifier,
    height: Dp = 44.dp,
    cornerRadius: Dp = 24.dp,
) {
    var widthPx by remember { mutableIntStateOf(1) }
    Box(
        modifier
            .height(height)
            .clip(RoundedCornerShape(cornerRadius))
            .background(trackColor)
            .onSizeChanged { widthPx = it.width.coerceAtLeast(1) }
            .pointerInput(Unit) {
                detectHorizontalDragGestures { change, _ ->
                    change.consume()
                    onValueChange((change.position.x / widthPx).coerceIn(0f, 1f))
                }
            }
            .pointerInput(Unit) {
                detectTapGestures { pos -> onValueChange((pos.x / widthPx).coerceIn(0f, 1f)) }
            },
    ) {
        Box(
            Modifier
                .fillMaxHeight()
                .fillMaxWidth(value)
                .background(accent),
        )
        Icon(
            icon,
            contentDescription = "Brightness",
            tint = iconTint,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 14.dp)
                .size(20.dp),
        )
    }
}
