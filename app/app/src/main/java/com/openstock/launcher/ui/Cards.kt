package com.openstock.launcher.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.openstock.launcher.skin.SkinSpec

@Composable
fun MediaCard(spec: SkinSpec) {
    val media = MockMediaItem
    Row(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(spec.shapes.cardCorner))
            .background(spec.palette.tileInactiveFill)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            Modifier
                .size(56.dp)
                .clip(RoundedCornerShape(spec.shapes.cardCorner - 6.dp))
                .background(media.brush),
        )
        Spacer(Modifier.width(12.dp))
        Column(Modifier.weight(1f)) {
            Text(
                media.title,
                color = spec.palette.textPrimary,
                fontSize = 15.sp,
                fontWeight = FontWeight(500),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(media.artist, color = spec.palette.textSecondary, fontSize = 12.sp, maxLines = 1)
        }
        var playing by remember { mutableStateOf(true) }
        Icon(
            Icons.Filled.SkipPrevious,
            contentDescription = "Previous",
            tint = spec.palette.textPrimary,
            modifier = Modifier
                .size(28.dp)
                .clickable { },
        )
        Spacer(Modifier.width(10.dp))
        Icon(
            if (playing) Icons.Filled.Pause else Icons.Filled.PlayArrow,
            contentDescription = if (playing) "Pause" else "Play",
            tint = spec.palette.textPrimary,
            modifier = Modifier
                .size(28.dp)
                .clickable { playing = !playing },
        )
        Spacer(Modifier.width(10.dp))
        Icon(
            Icons.Filled.SkipNext,
            contentDescription = "Next",
            tint = spec.palette.textPrimary,
            modifier = Modifier
                .size(28.dp)
                .clickable { },
        )
    }
}

@Composable
fun NotificationList(spec: SkinSpec, modifier: Modifier = Modifier) {
    LazyColumn(modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(MockNotifications.size) { i ->
            val n = MockNotifications[i]
            Row(
                Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(spec.palette.tileInactiveFill)
                    .padding(12.dp),
                verticalAlignment = Alignment.Top,
            ) {
                Box(
                    Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(n.tint),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        n.app.take(1),
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight(600),
                    )
                }
                Spacer(Modifier.width(10.dp))
                Column(Modifier.weight(1f)) {
                    Row {
                        Text(n.app, color = spec.palette.textSecondary, fontSize = 12.sp)
                        Spacer(Modifier.weight(1f))
                        Text(n.time, color = spec.palette.textSecondary, fontSize = 11.sp)
                    }
                    Text(
                        n.title,
                        color = spec.palette.textPrimary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight(500),
                    )
                    Text(
                        n.body,
                        color = spec.palette.textSecondary,
                        fontSize = 12.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
        }
    }
}
