package com.example.tradelearn.ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tradelearn.data.Chapter
import com.example.tradelearn.ui.components.GuidebookButton
import com.example.tradelearn.ui.theme.*
import kotlin.math.sin

@Composable
fun HomeMapScreen(viewModel: HomeViewModel = viewModel(), onChapterClick: (Int) -> Unit) {
        val modules by viewModel.modules.collectAsState()
        val chapters by viewModel.chapters.collectAsState()

        LazyColumn(
                modifier = Modifier.fillMaxSize().background(Slate50),
                contentPadding = PaddingValues(bottom = 120.dp)
        ) {
                // Find the first module to act as Unit 1
                val firstModule = modules.firstOrNull()

                item {
                        // Unit Header
                        Column(
                                modifier =
                                        Modifier.fillMaxWidth()
                                                .padding(horizontal = 16.dp, vertical = 24.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                                Text(
                                        text = "UNIT 1",
                                        color = Slate500,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        letterSpacing = 2.sp
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                        text = firstModule?.title ?: "Market Fundamentals",
                                        color = Slate900,
                                        fontWeight = FontWeight.ExtraBold,
                                        fontSize = 24.sp,
                                        textAlign = TextAlign.Center
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                        text = firstModule?.description
                                                        ?: "Master the basics of stocks, charts, and trading psychology.",
                                        color = Slate500,
                                        fontSize = 14.sp,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.padding(horizontal = 32.dp)
                                )
                        }

                        // Floating Guidebook Button
                        Row(
                                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                                horizontalArrangement = Arrangement.Start
                        ) { GuidebookButton(onClick = { /* TODO */}) }
                }

                // Map Nodes
                if (firstModule != null) {
                        val moduleChapters = chapters.filter { it.moduleId == firstModule.id }

                        // Assume the first one is completed, second is current, rest are locked for
                        // visual demo
                        // In reality, this would map to user progress state

                        itemsIndexed(moduleChapters) { index, chapter ->
                                val state =
                                        when (index) {
                                                0 -> NodeState.COMPLETED
                                                1 -> NodeState.CURRENT
                                                else -> NodeState.LOCKED
                                        }

                                PathNode(
                                        chapter = chapter,
                                        index = index,
                                        state = state,
                                        onClick = {
                                                if (state != NodeState.LOCKED)
                                                        onChapterClick(chapter.id)
                                        }
                                )
                        }
                }
        }
}

enum class NodeState {
        COMPLETED,
        CURRENT,
        LOCKED
}

@Composable
fun PathNode(chapter: Chapter, index: Int, state: NodeState, onClick: () -> Unit) {
        // Calculate the snake-like horizontal offset
        // Multiplier determines how wide the snake path is
        val offsetProgress = (index * 0.8f) // determines the phase
        val horizontalOffset = (sin(offsetProgress) * 60).dp

        Column(
                modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
        ) {
                Box(
                        modifier =
                                Modifier.offset(x = horizontalOffset)
                                        .clickable(
                                                enabled = state != NodeState.LOCKED,
                                                onClick = onClick
                                        ),
                        contentAlignment = Alignment.Center
                ) {
                        // Connector Line to next node (Drawing behind the circle)
                        // We draw a dashed line straight down for simplicity right now
                        if (index < 5) { // Replace with actual chapter count max
                                Box(
                                        modifier =
                                                Modifier.align(Alignment.BottomCenter)
                                                        .offset(y = 60.dp) // Push it below the node
                                                        .size(
                                                                width = 4.dp,
                                                                height = 40.dp
                                                        ) // The line segment
                                                        .drawBehind {
                                                                drawLine(
                                                                        color = Slate300,
                                                                        start =
                                                                                androidx.compose.ui
                                                                                        .geometry
                                                                                        .Offset(
                                                                                                size.width /
                                                                                                        2,
                                                                                                0f
                                                                                        ),
                                                                        end =
                                                                                androidx.compose.ui
                                                                                        .geometry
                                                                                        .Offset(
                                                                                                size.width /
                                                                                                        2,
                                                                                                size.height
                                                                                        ),
                                                                        strokeWidth = 8.dp.toPx(),
                                                                        pathEffect =
                                                                                PathEffect
                                                                                        .dashPathIntervals(
                                                                                                floatArrayOf(
                                                                                                        15f,
                                                                                                        15f
                                                                                                ),
                                                                                                0f
                                                                                        )
                                                                )
                                                        }
                                )
                        }

                        // Draw Node based on state
                        when (state) {
                                NodeState.COMPLETED -> CompletedNode(chapter)
                                NodeState.CURRENT -> CurrentNode(chapter)
                                NodeState.LOCKED -> LockedNode(chapter)
                        }
                }

                // Label Below Node
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                        text = chapter.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = if (state == NodeState.LOCKED) Slate400 else Slate600,
                        modifier = Modifier.offset(x = horizontalOffset)
                )
        }
}

@Composable
fun CompletedNode(chapter: Chapter) {
        Box(
                modifier =
                        Modifier.size(80.dp)
                                .clip(CircleShape)
                                .background(Yellow400)
                                .padding(bottom = 4.dp) // The 3D border effect
                                .background(Yellow500)
        ) {
                Box(
                        modifier = Modifier.fillMaxSize().clip(CircleShape).background(Yellow400),
                        contentAlignment = Alignment.Center
                ) {
                        Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "Completed",
                                tint = Color.White,
                                modifier = Modifier.size(40.dp)
                        )
                }

                // Crown/Level badge
                Box(
                        modifier =
                                Modifier.align(Alignment.TopEnd)
                                        .offset(x = 4.dp, y = (-4).dp)
                                        .background(Yellow300, RoundedCornerShape(12.dp))
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                        Text(
                                "LVL 5",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Yellow800
                        )
                }
        }
}

@Composable
fun CurrentNode(chapter: Chapter) {
        // Bouncing animation for the tooltip
        val infiniteTransition = rememberInfiniteTransition(label = "bounce")
        val yOffset by
                infiniteTransition.animateFloat(
                        initialValue = -10f,
                        targetValue = 0f,
                        animationSpec =
                                infiniteRepeatable(
                                        animation = tween(1000, easing = FastOutSlowInEasing),
                                        repeatMode = RepeatMode.Reverse
                                ),
                        label = "bounce"
                )

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                // Tooltip
                Box(modifier = Modifier.offset(y = yOffset.dp).padding(bottom = 8.dp)) {
                        Surface(
                                color = Color.White,
                                shape = RoundedCornerShape(12.dp),
                                shadowElevation = 8.dp
                        ) {
                                Text(
                                        text = "Start here!",
                                        color = Blue500,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        modifier =
                                                Modifier.padding(
                                                        horizontal = 12.dp,
                                                        vertical = 8.dp
                                                )
                                )
                        }
                }

                // The Node itself
                Box(
                        modifier =
                                Modifier.size(96.dp)
                                        .clip(CircleShape)
                                        .background(Blue700) // Shadow bottom
                                        .padding(bottom = 6.dp)
                                        .background(Blue500) // Main button
                ) {
                        Box(
                                modifier =
                                        Modifier.fillMaxSize()
                                                .clip(CircleShape)
                                                .background(Blue500),
                                contentAlignment = Alignment.Center
                        ) {
                                // Pulsing ring could be added here using another modifier behind
                                // the icon
                                Icon(
                                        imageVector = Icons.Default.PlayArrow,
                                        contentDescription = "Current Lesson",
                                        tint = Color.White,
                                        modifier = Modifier.size(48.dp)
                                )
                        }
                }
        }
}

@Composable
fun LockedNode(chapter: Chapter) {
        Box(
                modifier =
                        Modifier.size(80.dp)
                                .clip(CircleShape)
                                .background(Slate300)
                                .padding(bottom = 4.dp)
                                .background(Slate200)
        ) {
                Box(
                        modifier = Modifier.fillMaxSize().clip(CircleShape).background(Slate200),
                        contentAlignment = Alignment.Center
                ) {
                        Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = "Locked",
                                tint = Slate400,
                                modifier = Modifier.size(32.dp)
                        )
                }
        }
}
