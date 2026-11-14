package com.openstock.launcher.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BatteryFull
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SignalCellularAlt
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.openstock.launcher.skin.SkinSpec
import com.openstock.launcher.skin.SkinType
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun HomeScreen(skinType: SkinType, spec: SkinSpec) {
    val wallpaper = remember(skinType) {
        Brush.verticalGradient(
            listOf(
                Color(0xFF0B0E1A),
                when (skinType) {
                    SkinType.HYPEROS -> Color(0xFF1B2733)
                    SkinType.COLOROS -> Color(0xFF16211C)
                    SkinType.ONEUI -> Color(0xFF1A1C2B)
                },
            ),
        )
    }

    val time = remember { LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")) }
    val date = remember { LocalDate.now().format(DateTimeFormatter.ofPattern("EEEE, MMMM d")) }

    Box(
        Modifier
            .fillMaxSize()
            .background(wallpaper),
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .statusBarsPadding(),
        ) {
            StatusBarMock(spec)
            Spacer(Modifier.height(40.dp))

            Column(
                Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    time,
                    color = spec.palette.textPrimary,
                    fontSize = 54.sp,
                    fontWeight = FontWeight(600),
                )
                Text(date, color = spec.palette.textSecondary, fontSize = 14.sp)
            }

            Spacer(Modifier.weight(1f))
            AppGrid(spec)
            Spacer(Modifier.height(20.dp))

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                repeat(3) { i ->
                    Box(
                        Modifier
                            .padding(horizontal = 4.dp)
                            .size(if (i == 0) 8.dp else 6.dp)
                            .background(
                                if (i == 0) spec.palette.accent else spec.palette.textSecondary,
                                CircleShape,
                            ),
                    )
                }
            }
            Spacer(Modifier.height(16.dp))

            SearchPill(spec)
            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun StatusBarMock(spec: SkinSpec) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(32.dp)
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text("11:26", color = spec.palette.textPrimary, fontSize = 13.sp)
        Spacer(Modifier.weight(1f))
        Icon(
            Icons.Filled.Wifi,
            contentDescription = "Wi-Fi",
            tint = spec.palette.textPrimary,
            modifier = Modifier.size(15.dp),
        )
        Spacer(Modifier.width(6.dp))
        Icon(
            Icons.Filled.SignalCellularAlt,
            contentDescription = "Signal",
            tint = spec.palette.textPrimary,
            modifier = Modifier.size(15.dp),
        )
        Spacer(Modifier.width(6.dp))
        Icon(
            Icons.Filled.BatteryFull,
            contentDescription = "Battery",
            tint = spec.palette.textPrimary,
            modifier = Modifier.size(17.dp),
        )
    }
}

@Composable
private fun AppGrid(spec: SkinSpec) {
    Column(verticalArrangement = Arrangement.spacedBy(18.dp)) {
        MockApps.chunked(4).forEach { row ->
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                row.forEach { app -> AppIcon(app, spec) }
            }
        }
    }
}

@Composable
private fun AppIcon(app: MockApp, spec: SkinSpec) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.width(72.dp)) {
        Box(
            Modifier
                .size(52.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(app.brush),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                app.name.take(1),
                color = Color.White.copy(alpha = 0.95f),
                fontSize = 22.sp,
                fontWeight = FontWeight(600),
            )
        }
        Spacer(Modifier.height(6.dp))
        Text(app.name, color = spec.palette.textPrimary, fontSize = 11.sp, maxLines = 1)
    }
}

@Composable
private fun SearchPill(spec: SkinSpec) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 28.dp)
            .height(44.dp)
            .clip(CircleShape)
            .background(Color(0x1FFFFFFF))
            .clickable { },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Spacer(Modifier.width(16.dp))
        Icon(
            Icons.Filled.Search,
            contentDescription = "Search",
            tint = spec.palette.textSecondary,
            modifier = Modifier.size(18.dp),
        )
        Spacer(Modifier.width(10.dp))
        Text("Search apps", color = spec.palette.textSecondary, fontSize = 13.sp)
    }
}
