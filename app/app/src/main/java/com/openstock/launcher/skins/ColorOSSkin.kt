package com.openstock.launcher.skins

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brightness6
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.openstock.launcher.skin.ContentAnchor
import com.openstock.launcher.skin.EaseOutQuint
import com.openstock.launcher.skin.ExpandMode
import com.openstock.launcher.skin.LayoutSpec
import com.openstock.launcher.skin.MotionSpec
import com.openstock.launcher.skin.Palette
import com.openstock.launcher.skin.PanelBehavior
import com.openstock.launcher.skin.ShapeSpec
import com.openstock.launcher.skin.SkinSpec
import com.openstock.launcher.ui.BrightnessSlider
import com.openstock.launcher.ui.CircleToggle
import com.openstock.launcher.ui.MediaCard
import com.openstock.launcher.ui.MockTiles
import com.openstock.launcher.ui.NotificationList
import com.openstock.launcher.ui.ToggleGrid
import com.openstock.launcher.ui.rememberTileStates

val ColorOSSpec = SkinSpec(
    name = "coloros",
    specIds = listOf("SC-CO-001", "SC-CO-003"),
    palette = Palette(
        accent = Color(0xFF2FD593),
        surfaceGlass = Color(0xA8141614),
        textPrimary = Color(0xFFF3F6F4),
        textSecondary = Color(0xB3F3F6F4),
        tileActiveIcon = Color(0xFF04241A),
        tileInactiveFill = Color(0x22FFFFFF),
        tileInactiveIcon = Color(0xFFE9EFEC),
    ),
    shapes = ShapeSpec(
        panelCorner = 32.dp,
        tileCorner = 24.dp,
        cardCorner = 24.dp,
        sliderCorner = 24.dp,
    ),
    layout = LayoutSpec(
        columns = 4,
        tileSize = 56.dp,
        tileGap = 16.dp,
        tileIcon = 22.dp,
        tilesAreCircles = true,
        tileGlow = true,
    ),
    panel = PanelBehavior(
        expandMode = ExpandMode.SingleStage,
        openThreshold = 0.32f,
        fullThreshold = 0.55f,
        cornerRadius = 32.dp,
        glassColor = Color(0xA8141614),
        scrimStrength = 0.35f,
        edgeInset = 0.dp,
        lightBleed = true,
        anchor = ContentAnchor.Top,
    ),
    motion = MotionSpec(
        useSpring = false,
        damping = 1f,
        stiffness = 380f,
        durationMs = 320,
        easing = EaseOutQuint,
    ),
)

@Composable
fun ColorOSPanel() {
    val spec = ColorOSSpec
    val tiles = rememberTileStates(MockTiles, key = "coloros")
    var brightness by remember { mutableFloatStateOf(0.6f) }

    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(Modifier.height(20.dp))
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(Modifier.weight(1f))
            Text(
                "Quick settings",
                color = spec.palette.textPrimary,
                fontSize = 15.sp,
                fontWeight = FontWeight(500),
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(2f),
            )
            Box(Modifier.weight(1f), contentAlignment = Alignment.CenterEnd) {
                Icon(
                    Icons.Filled.Edit,
                    contentDescription = "Edit tiles",
                    tint = spec.palette.textSecondary,
                    modifier = Modifier
                        .size(18.dp)
                        .clickable { },
                )
            }
        }
        Spacer(Modifier.height(20.dp))

        ToggleGrid(tiles.take(8), spec.layout.columns, centered = true) { CircleToggle(it, spec) }

        Spacer(Modifier.height(24.dp))
        BrightnessSlider(
            value = brightness,
            onValueChange = { brightness = it },
            accent = spec.palette.accent,
            trackColor = spec.palette.tileInactiveFill,
            icon = Icons.Filled.Brightness6,
            iconTint = spec.palette.tileActiveIcon,
            height = 40.dp,
            cornerRadius = spec.shapes.sliderCorner,
        )

        Spacer(Modifier.height(14.dp))
        MediaCard(spec)

        Spacer(Modifier.height(16.dp))
        Text("Notifications", color = spec.palette.textSecondary, fontSize = 12.sp)
        Spacer(Modifier.height(8.dp))
        NotificationList(spec, Modifier.weight(1f))
        Spacer(Modifier.height(12.dp))
    }
}
