package com.example.tradelearn.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tradelearn.ui.theme.*

/** A floating guidebook button, matching the dark slate button in the React UI layout. */
@Composable
fun GuidebookButton(onClick: () -> Unit) {
    Surface(
            onClick = onClick,
            shape = RoundedCornerShape(12.dp),
            color = Slate800,
            border = BorderStroke(2.dp, Slate700),
            modifier = Modifier.padding(16.dp)
    ) {
        Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("📖", fontSize = 20.sp)
            Text(
                    text = "GUIDEBOOK",
                    color = Slate200,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    letterSpacing = 1.sp
            )
        }
    }
}

/**
 * The top bar used in Lessons and quizzes, showing the close button, a progress bar, and hearts.
 */
@Composable
fun LessonTopBar(
        progress: Float, // 0.0 to 1.0
        hearts: Int,
        onCloseClick: () -> Unit
) {
    Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Close Button
        IconButton(onClick = onCloseClick, modifier = Modifier.size(40.dp).clip(CircleShape)) {
            Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                    tint = Slate400,
                    modifier = Modifier.size(28.dp)
            )
        }

        // Progress Bar (Green fill)
        Box(
                modifier =
                        Modifier.weight(1f)
                                .height(16.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Slate100)
        ) {
            BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                val width = maxWidth * progress
                Box(
                        modifier =
                                Modifier.width(width)
                                        .fillMaxHeight()
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(Green500)
                ) {
                    // Shine effect
                    Box(
                            modifier =
                                    Modifier.padding(end = 8.dp, top = 4.dp)
                                            .size(width = 16.dp, height = 6.dp)
                                            .clip(RoundedCornerShape(3.dp))
                                            .background(Color.White.copy(alpha = 0.3f))
                                            .align(Alignment.TopEnd)
                    )
                }
            }
        }

        // Hearts
        Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Hearts",
                    tint = Red500,
                    modifier = Modifier.size(28.dp)
            )
            Text(
                    text = hearts.toString(),
                    color = Red500,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
            )
        }
    }
}
