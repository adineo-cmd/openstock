package com.openstock.launcher.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AirplanemodeActive
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.material.icons.filled.Cast
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.FlashlightOn
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material.icons.filled.ScreenRotation
import androidx.compose.material.icons.filled.Screenshot
import androidx.compose.material.icons.filled.VolumeOff
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material.icons.filled.WifiTethering
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

val MockTiles: List<MockTile> = listOf(
    MockTile("Wi-Fi", Icons.Filled.Wifi, initialOn = true),
    MockTile("Bluetooth", Icons.Filled.Bluetooth, initialOn = true),
    MockTile("Flashlight", Icons.Filled.FlashlightOn),
    MockTile("Airplane", Icons.Filled.AirplanemodeActive),
    MockTile("Rotate", Icons.Filled.ScreenRotation),
    MockTile("Dark mode", Icons.Filled.DarkMode, initialOn = true),
    MockTile("Hotspot", Icons.Filled.WifiTethering),
    MockTile("Location", Icons.Filled.LocationOn),
    MockTile("Silent", Icons.Filled.VolumeOff),
    MockTile("Cast", Icons.Filled.Cast),
    MockTile("Alerts", Icons.Filled.Notifications, initialOn = true),
    MockTile("Capture", Icons.Filled.Screenshot),
)

data class MockNotification(
    val app: String,
    val title: String,
    val body: String,
    val time: String,
    val tint: Color,
)

val MockNotifications: List<MockNotification> = listOf(
    MockNotification("Nimbus", "Backup finished", "12.4 GB synced to your cloud", "now", Color(0xFF3E6B4F)),
    MockNotification("Pulse", "Workout summary", "Evening run: 5.2 km in 27 min", "8 min", Color(0xFF6B3E5A)),
    MockNotification("Orbit", "Launch window", "Partly clear skies tonight — good seeing", "1 h", Color(0xFF2E4A6B)),
    MockNotification("System", "Storage almost full", "Photos can free up 3.1 GB", "2 h", Color(0xFF5A5A3E)),
)

data class MockMedia(
    val title: String,
    val artist: String,
    val brush: Brush,
)

val MockMediaItem = MockMedia(
    title = "Midnight Drive",
    artist = "Nova Circuit",
    brush = Brush.linearGradient(listOf(Color(0xFF20242E), Color(0xFF3A2F55))),
)

data class MockApp(val name: String, val brush: Brush)

val MockApps: List<MockApp> = listOf(
    MockApp("Aura", Brush.linearGradient(listOf(Color(0xFF7A5C3E), Color(0xFFB08954)))),
    MockApp("Nimbus", Brush.linearGradient(listOf(Color(0xFF3E5A7A), Color(0xFF5B87B0)))),
    MockApp("Pulse", Brush.linearGradient(listOf(Color(0xFF7A3E5C), Color(0xFFB05B87)))),
    MockApp("Vertex", Brush.linearGradient(listOf(Color(0xFF3E7A6B), Color(0xFF54B097)))),
    MockApp("Echo", Brush.linearGradient(listOf(Color(0xFF55557A), Color(0xFF7C7CB0)))),
    MockApp("Drift", Brush.linearGradient(listOf(Color(0xFF6B6B3E), Color(0xFF9C9C54)))),
    MockApp("Orbit", Brush.linearGradient(listOf(Color(0xFF2E4A6B), Color(0xFF4A7AB0)))),
    MockApp("Lumen", Brush.linearGradient(listOf(Color(0xFF7A523E), Color(0xFFB07654)))),
    MockApp("Quartz", Brush.linearGradient(listOf(Color(0xFF4E4E5E), Color(0xFF77778F)))),
    MockApp("Zephyr", Brush.linearGradient(listOf(Color(0xFF3E7A4F), Color(0xFF54B072)))),
    MockApp("Halo", Brush.linearGradient(listOf(Color(0xFF6B3E3E), Color(0xFFB05454)))),
    MockApp("Cinder", Brush.linearGradient(listOf(Color(0xFF444444), Color(0xFF6E6E6E)))),
    MockApp("Flux", Brush.linearGradient(listOf(Color(0xFF4F3E7A), Color(0xFF7254B0)))),
    MockApp("Prism", Brush.linearGradient(listOf(Color(0xFF3E6B7A), Color(0xFF549EB0)))),
    MockApp("Onyx", Brush.linearGradient(listOf(Color(0xFF2B2B2B), Color(0xFF505050)))),
    MockApp("Terra", Brush.linearGradient(listOf(Color(0xFF5E5237), Color(0xFF8F7C4E)))),
)
