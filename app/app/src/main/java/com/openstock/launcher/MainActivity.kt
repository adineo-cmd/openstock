package com.openstock.launcher

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.openstock.launcher.skin.PanelBehavior
import com.openstock.launcher.skin.SkinSpec
import com.openstock.launcher.skin.SkinType
import com.openstock.launcher.skin.nextSkin
import com.openstock.launcher.skins.ColorOSPanel
import com.openstock.launcher.skins.ColorOSSpec
import com.openstock.launcher.skins.HyperOSPanel
import com.openstock.launcher.skins.HyperOSSpec
import com.openstock.launcher.skins.OneUIPanel
import com.openstock.launcher.skins.OneUISpec
import com.openstock.launcher.ui.HomeScreen
import com.openstock.launcher.ui.PanelState
import com.openstock.launcher.ui.PullPanel
import com.openstock.launcher.ui.SkinSwitcher

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OpenStockApp()
        }
    }
}

fun specFor(type: SkinType): SkinSpec = when (type) {
    SkinType.HYPEROS -> HyperOSSpec
    SkinType.COLOROS -> ColorOSSpec
    SkinType.ONEUI -> OneUISpec
}

@Composable
fun OpenStockApp() {
    var skinType by remember { mutableStateOf(SkinType.HYPEROS) }
    val spec = specFor(skinType)
    val panelState = remember(skinType) { PanelState(spec.panel) }

    Box(
        Modifier
            .fillMaxSize()
            .background(Color.Black),
    ) {
        HomeScreen(skinType, spec)

        PullPanel(spec.panel, spec.motion, panelState) { stage ->
            when (skinType) {
                SkinType.HYPEROS -> HyperOSPanel(stage)
                SkinType.COLOROS -> ColorOSPanel()
                SkinType.ONEUI -> OneUIPanel()
            }
        }

        if (!panelState.isOpen) {
            HintChip(
                Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 88.dp),
            )
        }

        SkinSwitcher(
            current = skinType,
            accent = spec.palette.accent,
            onSelect = { skinType = nextSkin(it) },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 28.dp),
        )
    }
}

@Composable
private fun HintChip(modifier: Modifier = Modifier) {
    Box(
        modifier
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0x88000000))
            .padding(horizontal = 16.dp, vertical = 8.dp),
    ) {
        Text("swipe down from the top", color = Color(0xB3FFFFFF), fontSize = 12.sp)
    }
}
