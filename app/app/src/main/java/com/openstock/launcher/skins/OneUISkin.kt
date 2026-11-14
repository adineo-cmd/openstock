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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.openstock.launcher.skin.ContentAnchor
import com.openstock.launcher.skin.EaseOutCubic
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
import com.openstock.launcher.ui.ToggleGrid
import com.openstock.launcher.ui.rememberTileStates
import java.time.LocalDate
import java.time.format.DateTimeFormatter

val OneUISpec = SkinSpec(
    name = "oneui",
    specIds = listOf("SC-OU-001", "SC-OU-003"),
    palette = Palette(
        accent = Color(0xFF7D97FF),
        surfaceGlass = Color(0xD10F1116),
        textPrimary = Color(0xFFF4F5F8),
        textSecondary = Color(0xB3F4F5F8),
        tileActiveIcon = Color(0xFF10142E),
        tileInactiveFill = Color(0x26FFFFFF),
        tileInactiveIcon = Color(0xFFECEEF5),
    ),
    shapes = ShapeSpec(
        panelCorner = 30.dp,
        tileCorner = 26.dp,
        cardCorner = 26.dp,
        sliderCorner = 26.dp,
    ),
    layout = LayoutSpec(
        columns = 3,
        tileSize = 52.dp,
        tileGap = 20.dp,
        tileIcon = 22.dp,
        tilesAreCircles = true,
        tileLabel = true,
    ),
    panel = PanelBehavior(
        expandMode = ExpandMode.SingleStage,
        openThreshold = 0.32f,
        fullThreshold = 0.55f,
        cornerRadius = 30.dp,
        glassColor = Color(0xD10F1116),
        scrimStrength = 0.50f,
        edgeInset = 0.dp,
        lightBleed = false,
        anchor = ContentAnchor.Top,
    ),
    motion = MotionSpec(
        useSpring = false,
        damping = 1f,
        stiffness = 380f,
        durationMs = 260,
        easing = EaseOutCubic,
    ),
)

@Composable
fun OneUIPanel() {
    val spec = OneUISpec
    val tiles = rememberTileStates(MockTiles, key = "oneui")
    var brightness by remember { mutableFloatStateOf(0.6f) }
    val date = remember { LocalDate.now().format(DateTimeFormatter.ofPattern("EEE, MMM d")) }

    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
    ) {
        Spacer(Modifier.height(28.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                Text(
                    date,
                    color = spec.palette.textPrimary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight(600),
                )
                Spacer(Modifier.height(4.dp))
                Text("OpenStock · oneui", color = spec.palette.textSecondary, fontSize = 12.sp)
            }
            Icon(
                Icons.Filled.Settings,
                contentDescription = "Settings",
                tint = spec.palette.textSecondary,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .clickable { },
            )
            Spacer(Modifier.width(18.dp))
            Icon(
                Icons.Filled.PowerSettingsNew,
                contentDescription = "Power",
                tint = spec.palette.textSecondary,
                modifier = Modifier.clickable { },
            )
        }

        Spacer(Modifier.weight(1f))

        ToggleGrid(tiles.take(9), spec.layout.columns) { CircleToggle(it, spec) }

        Spacer(Modifier.height(24.dp))
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

        Spacer(Modifier.height(16.dp))
        MediaCard(spec)
        Spacer(Modifier.height(28.dp))
    }
}
