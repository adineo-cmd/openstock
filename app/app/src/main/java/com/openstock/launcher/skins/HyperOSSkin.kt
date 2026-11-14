package com.openstock.launcher.skins

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brightness6
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material.icons.filled.Settings
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
import com.openstock.launcher.ui.MediaCard
import com.openstock.launcher.ui.MockTiles
import com.openstock.launcher.ui.NotificationList
import com.openstock.launcher.ui.PanelStage
import com.openstock.launcher.ui.SquareTile
import com.openstock.launcher.ui.ToggleGrid
import com.openstock.launcher.ui.rememberTileStates

val HyperOSSpec = SkinSpec(
    name = "hyperos",
    specIds = listOf("SC-HX-001", "SC-HX-003"),
    palette = Palette(
        accent = Color(0xFF3FD0E8),
        surfaceGlass = Color(0xB8101418),
        textPrimary = Color(0xFFF2F5F7),
        textSecondary = Color(0xB3F2F5F7),
        tileActiveIcon = Color(0xFF06272E),
        tileInactiveFill = Color(0x1FFFFFFF),
        tileInactiveIcon = Color(0xFFE8EDF0),
    ),
    shapes = ShapeSpec(
        panelCorner = 28.dp,
        tileCorner = 16.dp,
        cardCorner = 20.dp,
        sliderCorner = 22.dp,
    ),
    layout = LayoutSpec(
        columns = 4,
        tileSize = 64.dp,
        tileGap = 12.dp,
        tileIcon = 24.dp,
        tilesAreCircles = false,
    ),
    panel = PanelBehavior(
        expandMode = ExpandMode.TwoStage,
        compactStop = 0.45f,
        fullThreshold = 0.55f,
        openThreshold = 0.30f,
        cornerRadius = 28.dp,
        glassColor = Color(0xB8101418),
        scrimStrength = 0.40f,
        edgeInset = 12.dp,
        lightBleed = false,
        anchor = ContentAnchor.Top,
    ),
    motion = MotionSpec(
        useSpring = true,
        damping = 0.82f,
        stiffness = 380f,
        durationMs = 300,
        easing = EaseOutQuint,
    ),
)

@Composable
fun HyperOSPanel(stage: PanelStage) {
    val spec = HyperOSSpec
    val tiles = rememberTileStates(MockTiles, key = "hyperos")
    var brightness by remember { mutableFloatStateOf(0.6f) }

    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
    ) {
        Spacer(Modifier.height(if (stage == PanelStage.Full) 24.dp else 18.dp))

        val visibleTiles = if (stage == PanelStage.Full) tiles else tiles.take(8)
        ToggleGrid(visibleTiles, spec.layout.columns) { SquareTile(it, spec) }

        Spacer(Modifier.height(20.dp))
        BrightnessSlider(
            value = brightness,
            onValueChange = { brightness = it },
            accent = spec.palette.accent,
            trackColor = spec.palette.tileInactiveFill,
            icon = Icons.Filled.Brightness6,
            iconTint = spec.palette.tileActiveIcon,
            height = 44.dp,
            cornerRadius = spec.shapes.sliderCorner,
        )

        Spacer(Modifier.height(14.dp))
        MediaCard(spec)

        if (stage == PanelStage.Full) {
            Spacer(Modifier.height(18.dp))
            Text("Notifications", color = spec.palette.textSecondary, fontSize = 12.sp)
            Spacer(Modifier.height(8.dp))
            NotificationList(spec, Modifier.weight(1f))
            Spacer(Modifier.height(8.dp))
            HyperOSSettingsRow()
        }
    }
}

@Composable
private fun HyperOSSettingsRow() {
    val spec = HyperOSSpec
    Row(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            Icons.Filled.Settings,
            contentDescription = "Settings",
            tint = spec.palette.textSecondary,
            modifier = Modifier
                .width(20.dp)
                .clickable { },
        )
        Spacer(Modifier.width(10.dp))
        Text("Settings", color = spec.palette.textSecondary, fontSize = 12.sp)
        Spacer(Modifier.weight(1f))
        Icon(
            Icons.Filled.PowerSettingsNew,
            contentDescription = "Power",
            tint = spec.palette.textSecondary,
            modifier = Modifier.clickable { },
        )
    }
}
